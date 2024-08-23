package com.dnd.spaced.domain.word.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import java.util.List;

public record WordCandidateDto(String name, Category category) {
}
