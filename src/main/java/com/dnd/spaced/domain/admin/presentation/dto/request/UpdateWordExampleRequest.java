package com.dnd.spaced.domain.admin.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateWordExampleRequest(@Schema(description = "변경할 용어 예문 내용") @NotBlank String content) {

}
