/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.lcc.searchengine.spider;

import com.lcc.searchengine.lucene.Index;
import com.lcc.searchengine.lucene.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lcc
 * @since 2016-05-21 10:17
 */
public class LucenePipeline implements Pipeline, Closeable {
    public static final FieldType TYPE_NOT_INDEX = new FieldType();
    public static final FieldType TYPE_INDEX = new FieldType();
    public static final FieldType TYPE_INDEX_TERM = new FieldType();

    static {
        TYPE_NOT_INDEX.setIndexOptions(IndexOptions.NONE);
        TYPE_NOT_INDEX.setTokenized(false);
        TYPE_NOT_INDEX.setStored(true);
        TYPE_NOT_INDEX.freeze();

        TYPE_INDEX.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        TYPE_INDEX.setTokenized(true);
        TYPE_INDEX.setStored(true);
        TYPE_INDEX.freeze();

        TYPE_INDEX_TERM.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        TYPE_INDEX_TERM.setTokenized(true);
        TYPE_INDEX_TERM.setStored(true);
        TYPE_INDEX_TERM.setStoreTermVectors(true);
        TYPE_INDEX_TERM.freeze();
    }

    private final ReentrantLock lock = new ReentrantLock();
    private Index index;

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (index == null) {
            lock.lock();
            try {
                if (index == null) {
                    index = LuceneUtil.newIndex();
                }
            } finally {
                lock.unlock();
            }
        }
        Document doc = new Document();
        doc.add(new Field("title", resultItems.getAll().get("title").toString(), TYPE_NOT_INDEX));
        doc.add(new Field("url", resultItems.getRequest().getUrl(), TYPE_INDEX_TERM));
        doc.add(new Field("content", resultItems.getAll().get("content").toString(), TYPE_INDEX));
        index.update(resultItems.getRequest().getUrl(), doc);
    }

    @Override
    public void close() throws IOException {
        if (index != null) {
            LuceneUtil.closeIndex();
        }
    }
}
