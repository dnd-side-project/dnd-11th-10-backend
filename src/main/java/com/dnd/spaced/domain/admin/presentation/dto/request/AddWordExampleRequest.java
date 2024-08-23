package com.dnd.spaced.domain.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddWordExampleRequest(@NotBlank String content) {

}
