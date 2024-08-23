package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.WordExample;
import java.time.LocalDateTime;
import java.util.List;

public record MultipleWordInfoDto(
        Long id,
        String name,
        String meaning,
        String category,
        int viewCount,
        List<String> examples,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static MultipleWordInfoDto from(Word word) {
        List<String> examples = word.getExamples().stream().map(WordExample::getContent).toList();

        return new MultipleWordInfoDto(
                word.getId(),
                word.getName(),
                word.getMeaning(),
                word.getCategory().getName(),
                word.getViewCount(),
                examples,
                word.getCommentCount(),
                word.getCreatedAt(),
                word.getUpdatedAt()
        );
    }
}
