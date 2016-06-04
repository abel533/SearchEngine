/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.lcc.searchengine.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Map;

/**
 * @author lcc
 * @since 2016-05-22 12:11
 */
public class Index {
    private IndexWriter writer;
    private Directory directory;

    public Index(IndexWriter writer, Directory directory) {
        this.writer = writer;
        this.directory = directory;
    }

    public void add(Map<String, Object> data) {
        Document doc = new Document();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            doc.add(new Field(entry.getKey(), entry.getValue().toString(), TextField.TYPE_STORED));
        }
        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(String url, Map<String, Object> data) {
        Document doc = new Document();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            doc.add(new Field(entry.getKey(), entry.getValue().toString(), TextField.TYPE_STORED));
        }
        update(url, doc);
    }

    public void update(String url, Document doc) {
        try {
            writer.updateDocument(new Term("url", url), doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
            directory.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
