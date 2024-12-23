package com.dnd.spaced.domain.word.application.dto.request;

import org.springframework.data.domain.Pageable;

public record WordConditionInfoDto(String categoryName, String lastWordName, Pageable pageable) {
}
