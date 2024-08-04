package com.dnd.spaced.domain.word.presentation.dto;

import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import com.dnd.spaced.domain.word.presentation.dto.response.DetailWordInfoResponse;
import com.dnd.spaced.domain.word.presentation.dto.response.InputWordCandidateResponse;
import com.dnd.spaced.domain.word.presentation.dto.response.MultipleWordInfoResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordControllerMapper {

    public static MultipleWordInfoResponse to(List<MultipleWordInfoDto> dtos) {
        return MultipleWordInfoResponse.from(dtos);
    }

    public static DetailWordInfoResponse to(DetailWordInfoDto dto) {
        return DetailWordInfoResponse.from(dto);
    }

    public static InputWordCandidateResponse to(InputWordCandidateDto dto) {
        return new InputWordCandidateResponse(dto.candidates());
    }
}