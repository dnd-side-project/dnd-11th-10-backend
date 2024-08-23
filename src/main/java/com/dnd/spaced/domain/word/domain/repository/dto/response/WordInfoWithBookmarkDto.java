package com.dnd.spaced.domain.word.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Pronunciation;
import com.dnd.spaced.domain.word.domain.WordExample;
import java.time.LocalDateTime;
import java.util.List;

public record WordInfoWithBookmarkDto(
        Long wordId,
        String name,
        Pronunciation pronunciation,
        String meaning,
        Category category,
        List<WordExample> examples,
        int viewCount,
        int commentCount,
        int bookmarkCount,
        Long bookmarkId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
