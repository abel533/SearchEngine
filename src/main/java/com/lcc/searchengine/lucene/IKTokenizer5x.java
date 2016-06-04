/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.lcc.searchengine.lucene;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

/**
 * @author lcc
 * @since 2016-05-21 11:59
 */
public class IKTokenizer5x extends Tokenizer {
    private final CharTermAttribute termAtt = (CharTermAttribute) this.addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = (OffsetAttribute) this.addAttribute(OffsetAttribute.class);
    private final TypeAttribute typeAtt = (TypeAttribute) this.addAttribute(TypeAttribute.class);
    private IKSegmenter _IKImplement;
    private int endPosition;

    public IKTokenizer5x() {
        this._IKImplement = new IKSegmenter(this.input, true);
    }

    public IKTokenizer5x(boolean useSmart) {
        this._IKImplement = new IKSegmenter(this.input, useSmart);
    }

    public IKTokenizer5x(AttributeFactory factory) {
        super(factory);
        this._IKImplement = new IKSegmenter(this.input, true);
    }

    public boolean incrementToken() throws IOException {
        this.clearAttributes();
        Lexeme nextLexeme = this._IKImplement.next();
        if (nextLexeme != null) {
            this.termAtt.append(nextLexeme.getLexemeText());
            this.termAtt.setLength(nextLexeme.getLength());
            this.offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            this.endPosition = nextLexeme.getEndPosition();
            this.typeAtt.setType(nextLexeme.getLexemeTypeString());
            return true;
        } else {
            return false;
        }
    }

    public void reset() throws IOException {
        super.reset();
        this._IKImplement.reset(this.input);
    }

    public final void end() {
        int finalOffset = this.correctOffset(this.endPosition);
        this.offsetAtt.setOffset(finalOffset, finalOffset);
    }
}