package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.WordExample;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import java.time.LocalDateTime;
import java.util.List;

public record DetailWordInfoDto(
        Long id,
        String name,
        PronunciationInfoDto pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        int commentCount,
        int bookmarkCount,
        List<String> examples,
        boolean isMarked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static DetailWordInfoDto from(WordInfoWithBookmarkDto dto) {
        List<String> examples = dto.examples().stream().map(WordExample::getContent).toList();

        return new DetailWordInfoDto(
                dto.wordId(),
                dto.name(),
                new PronunciationInfoDto(dto.pronunciation().getEnglish()),
                dto.meaning(),
                dto.category().getName(),
                dto.viewCount() + 1,
                dto.commentCount(),
                dto.bookmarkCount(),
                examples,
                dto.bookmarkId() != null,
                dto.createdAt(),
                dto.updatedAt()
        );
    }

    public record PronunciationInfoDto(String english) {
    }
}
