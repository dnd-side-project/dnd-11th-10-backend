package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record InputWordCandidateResponse(
        @Schema(description = "용어 이름 검색어 후보군")
        List<WordCandidateInfoResponse> candidates
) {

    public static InputWordCandidateResponse from(List<InputWordCandidateDto> dtos) {
        List<WordCandidateInfoResponse> candidates = dtos.stream()
                                                         .map(dto ->
                                                                 new WordCandidateInfoResponse(
                                                                         dto.name(),
                                                                         dto.category()
                                                                 )
                                                         )
                                                         .toList();

        return new InputWordCandidateResponse(candidates);
    }

    private record WordCandidateInfoResponse(
            @Schema(description = "용어 이름 검색어 후보군")
            String name,

            @Schema(description = "용어 카테고리")
            String category
    ) {

    }
}
