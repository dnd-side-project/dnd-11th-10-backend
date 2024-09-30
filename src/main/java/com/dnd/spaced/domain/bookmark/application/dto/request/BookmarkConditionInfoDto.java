package com.dnd.spaced.domain.bookmark.application.dto.request;

import org.springframework.data.domain.Pageable;

public record BookmarkConditionInfoDto(String email, String category, Long lastBookmarkId, Pageable pageable) {
}
