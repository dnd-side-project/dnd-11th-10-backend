package com.dnd.spaced.domain.bookmark.presentation.dto.response;

import com.dnd.spaced.domain.bookmark.application.dto.response.BookmarkWordInfoDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record BookmarkWordResponse(List<BookmarkWordInfoResponse> words, Long lastBookmarkId) {

    public static BookmarkWordResponse from(List<BookmarkWordInfoDto> dtos) {
        if (dtos.isEmpty()) {
            return new BookmarkWordResponse(Collections.emptyList(), null);
        }

        List<BookmarkWordInfoResponse> words = dtos.stream()
                .map(BookmarkWordInfoResponse::from)
                .toList();

        return new BookmarkWordResponse(words, words.get(words.size() - 1).bookmarkId());
    }

    public record BookmarkWordInfoResponse(
            Long id,
            String name,
            PronunciationInfoResponse pronunciationInfo,
            String meaning,
            String category,
            int viewCount,
            String example,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            int commentCount,
            Long bookmarkId
    ) {

        public static BookmarkWordInfoResponse from(BookmarkWordInfoDto dto) {
            return new BookmarkWordInfoResponse(
                    dto.wordId(),
                    dto.name(),
                    new PronunciationInfoResponse(dto.pronunciationInfo().english()),
                    dto.meaning(),
                    dto.category(),
                    dto.viewCount(),
                    dto.example(),
                    dto.createdAt(),
                    dto.updatedAt(),
                    dto.commentCount(),
                    dto.bookmarkId()
            );
        }

        public record PronunciationInfoResponse(String english) {
        }
    }
}
