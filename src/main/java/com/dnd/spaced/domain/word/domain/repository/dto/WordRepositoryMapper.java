package com.dnd.spaced.domain.word.domain.repository.dto;

import com.dnd.spaced.domain.word.application.dto.request.SearchWordConditionInfoDto;
import com.dnd.spaced.domain.word.domain.repository.dto.request.SearchWordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordRepositoryMapper {

    public static WordConditionDto to(String categoryName, String lastWordName, Pageable pageable) {
        return new WordConditionDto(categoryName, lastWordName, pageable);
    }

    public static SearchWordConditionDto to(SearchWordConditionInfoDto dto) {
        return new SearchWordConditionDto(
                dto.name(),
                dto.pronunciation(),
                dto.lastWordName(),
                dto.category(),
                dto.pageable()
        );
    }
}
