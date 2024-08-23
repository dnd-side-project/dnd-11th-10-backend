package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record DetailWordInfoResponse(

        @Schema(description = "용어 ID")
        Long id,

        @Schema(description = "용어 이름")
        String name,

        @Schema(description = "용어 예문")
        List<WordExampleInfoResponse> examples,

        @Schema(description = "용어 발음 정보")
        PronunciationInfoResponse pronunciationInfo,

        @Schema(description = "용어 뜻")
        String meaning,

        @Schema(description = "용어 카테고리", allowableValues = {"개발", "디자인", "비즈니스"})
        String category,

        @Schema(description = "조회수")
        int viewCount,

        @Schema(description = "댓글 개수")
        int commentCount,

        @Schema(description = "북마크 개수")
        int bookmarkCount,

        @Schema(description = "북마크 여부")
        boolean isMarked
) {

    private record PronunciationInfoResponse(@Schema(description = "용어 영어 발음 기호") String english) {

    }

    private record WordExampleInfoResponse(
            @Schema(description = "용어 예문 ID")
            Long id,

            @Schema(description = "용어 예문")
            String example
    ) {
    }

    public static DetailWordInfoResponse from(DetailWordInfoDto dto) {
        List<WordExampleInfoResponse> wordExampleInfo = dto.examples()
                                                           .stream()
                                                           .map(target ->
                                                                   new WordExampleInfoResponse(
                                                                           target.id(),
                                                                           target.content()
                                                                   )
                                                           )
                                                           .toList();

        return new DetailWordInfoResponse(
                dto.id(),
                dto.name(),
                wordExampleInfo,
                new PronunciationInfoResponse(dto.pronunciationInfo().english()),
                dto.meaning(),
                dto.category(),
                dto.viewCount(),
                dto.commentCount(),
                dto.bookmarkCount(),
                dto.isMarked()
        );
    }
}
