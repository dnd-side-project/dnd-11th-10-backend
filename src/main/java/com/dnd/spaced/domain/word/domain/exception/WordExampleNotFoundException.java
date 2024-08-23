package com.dnd.spaced.domain.word.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class WordExampleNotFoundException extends BaseClientException {

    public WordExampleNotFoundException() {
        super(ExceptionCode.WORD_EXAMPLE_NOT_FOUND);
    }
}
