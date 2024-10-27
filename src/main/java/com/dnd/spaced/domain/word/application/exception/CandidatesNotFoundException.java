package com.dnd.spaced.domain.word.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class CandidatesNotFoundException extends BaseClientException {

    public CandidatesNotFoundException() {
        super(ExceptionCode.CANDIDATE_NOT_FOUND);
    }
}
