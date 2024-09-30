package com.dnd.spaced.domain.bookmark.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookmarkConditionRequest(
        @Schema(
                description = "용어 카테고리",
                allowableValues = {"전체 실무", "디자인", "개발", "비즈니스"},
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String category,

        @Schema(description = "마지막으로 조회한 북마크 Id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Long lastBookmarkId) {
}
