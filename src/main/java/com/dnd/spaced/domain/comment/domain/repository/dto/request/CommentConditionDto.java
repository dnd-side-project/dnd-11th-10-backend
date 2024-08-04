package com.dnd.spaced.domain.comment.domain.repository.dto.request;

import org.springframework.data.domain.Pageable;

public record CommentConditionDto(Long lastCommentId, Integer lastLikeCount, Pageable pageable) {
}
