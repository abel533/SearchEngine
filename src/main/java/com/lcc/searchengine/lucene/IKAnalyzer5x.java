/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.lcc.searchengine.lucene;

import org.apache.lucene.analysis.Analyzer;

/**
 * @author lcc
 * @since 2016-05-21 12:00
 */
public class IKAnalyzer5x extends Analyzer {

    private boolean useSmart;

    public IKAnalyzer5x() {
        this(false);
    }

    public IKAnalyzer5x(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public boolean useSmart() {
        return this.useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
        IKTokenizer5x _IKTokenizer = new IKTokenizer5x(this.useSmart);
        return new TokenStreamComponents(_IKTokenizer);
    }
}
