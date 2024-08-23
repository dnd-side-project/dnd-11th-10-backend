package com.dnd.spaced.domain.bookmark.application.dto.response;

import com.dnd.spaced.domain.bookmark.domain.repository.dto.response.BookmarkWordDto;
import com.dnd.spaced.domain.word.domain.WordExample;
import java.time.LocalDateTime;
import java.util.List;

public record BookmarkWordInfoDto(
        Long wordId,
        String name,
        PronunciationInfoDto pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        List<String> examples,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long bookmarkId
) {

    public static BookmarkWordInfoDto from(BookmarkWordDto dto) {
        List<String> examples = dto.examples().stream().map(WordExample::getContent).toList();

        return new BookmarkWordInfoDto(
                dto.wordId(),
                dto.name(),
                new PronunciationInfoDto(dto.pronunciation().getEnglish()),
                dto.meaning(),
                dto.category().getName(),
                dto.viewCount(),
                examples,
                dto.createdAt(),
                dto.updatedAt(),
                dto.bookmarkId()
        );
    }

    public record PronunciationInfoDto(String english) {
    }
}
