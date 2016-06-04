/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.lcc.searchengine.lucene;

import com.lcc.searchengine.model.PageInfo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.sandbox.queries.DuplicateFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;

/**
 * @author lcc
 * @since 2016-05-21 11:52
 */
public class LuceneUtil {
    public static final String INDEX_PATH;
    private static final Analyzer ANALYZER = new IKAnalyzer5x(true);
    private static int count = 0;
    private static Index index = null;

    static {
        String index = "lucene/index";
        try {
            if (!ResourceUtils.getFile(index).exists()) {
                index = "classpath:lucene/index";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            INDEX_PATH = index;
        }
    }

    /**
     * 索引数据
     */
    public synchronized static Index newIndex() {
        try {
            count++;
            if (index != null) {
                return index;
            }
            String path = ResourceUtils.getFile(INDEX_PATH).getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            Directory dictionary = new SimpleFSDirectory(FileSystems.getDefault().getPath(path));
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ANALYZER);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter indexWriter = new IndexWriter(dictionary, indexWriterConfig);
            index = new Index(indexWriter, dictionary);
            return index;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭索引
     */
    public synchronized static void closeIndex() {
        if (index != null) {
            count--;
            if (count < 1) {
                index.close();
                index = null;
                count = 0;
            }
        }
    }

    /**
     * 查询
     *
     * @param q
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static PageInfo query(String q, int pageNum, int pageSize) {
        try {
            String path = ResourceUtils.getFile(INDEX_PATH).getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            Directory dictionary = new SimpleFSDirectory(FileSystems.getDefault().getPath(path));
            DirectoryReader reader = DirectoryReader.open(dictionary);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("content", ANALYZER);
            Query query = parser.parse(q);
            DuplicateFilter filter = new DuplicateFilter("content");
            filter.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
            TopDocs topDocs = searcher.search(query, filter, (pageNum + 1) * pageSize);
            ScoreDoc[] hits = topDocs.scoreDocs;
            Integer total = topDocs.totalHits;
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
            int start = (pageNum - 1) * pageSize;
            int end = start + pageSize;
            for (int i = start; i < end && i < total; i++) {
                Map<String, String> map = new HashMap<String, String>();
                Document hitDoc = searcher.doc(hits[i].doc);
                for (Iterator<IndexableField> iter = hitDoc.iterator(); iter.hasNext(); ) {
                    IndexableField field = iter.next();
                    String value = field.stringValue();
                    if (field.name().equals("content") && value.length() > 200) {
                        value = value.substring(0, 200) + "...";
                    }
                    map.put(field.name(), value);
                }
                result.add(map);
            }
            reader.close();
            dictionary.close();
            return new PageInfo(result, pageNum, pageSize, total, 8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
