package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.Word;
import java.time.LocalDateTime;

public record MultipleWordInfoDto(
        Long id,
        String name,
        String meaning,
        String category,
        int viewCount,
        String example,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static MultipleWordInfoDto from(Word word) {
        return new MultipleWordInfoDto(
                word.getId(),
                word.getName(),
                word.getMeaning(),
                word.getCategory().getName(),
                word.getViewCount(),
                word.getExample(),
                word.getCommentCount(),
                word.getCreatedAt(),
                word.getUpdatedAt()
        );
    }
}
