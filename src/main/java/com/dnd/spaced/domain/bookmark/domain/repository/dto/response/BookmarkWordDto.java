package com.dnd.spaced.domain.bookmark.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Pronunciation;

import java.time.LocalDateTime;

public record BookmarkWordDto(
        Long wordId,
        String name,
        Pronunciation pronunciation,
        String meaning,
        Category category,
        int viewCount,
        String example,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        int commentCount,
        Long bookmarkId
) {
}
