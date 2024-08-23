package com.dnd.spaced.domain.admin.presentation;

import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.presentation.dto.request.AddWordExampleRequest;
import com.dnd.spaced.domain.admin.presentation.dto.request.UpdateWordExampleRequest;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.admin.presentation.dto.response.ReportListResponse;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.docs.annotation.ExcludeCommonHeaderSpec;
import com.dnd.spaced.global.docs.annotation.NotRequiredCommonHeaderSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "관리자 관련 API", description = "용어 생성, 용어 삭제, 용어 수정, 신고 수락, 신고 무시, 신고 목록 조회")
public interface SwaggerAdminController {

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 등록", description = "관리자가 새로운 용어를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE
    })
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<Void> createWord(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @RequestBody @Valid AdminWordRequestDto wordRequestDto
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 삭제", description = "관리자가 용어를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.WORD_NOT_FOUND
    })
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<Void> deleteWord(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long id
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 수정", description = "관리자가 용어를 수정합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.WORD_NOT_FOUND
    })
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<Void> updateWord(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long id,
            @RequestBody @Valid AdminWordRequestDto wordRequestDto
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 조회", description = "관리자가 용어를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AdminWordResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.WORD_NOT_FOUND
    })
    ResponseEntity<AdminWordResponse> getWord(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long id
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "신고 수락", description = "관리자가 신고를 수락합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.REPORTED_COMMENT_NOT_FOUND,
            ExceptionCode.COMMENT_NOT_FOUND
    })
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<Void> acceptReport(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "신고 ID") @PathVariable Long id
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "신고 무시", description = "관리자가 신고를 무시합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.REPORT_NOT_FOUND
    })
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<Void> ignoreReport(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "신고 ID") @PathVariable Long id
    );

    @NotRequiredCommonHeaderSpec
    @Operation(summary = "신고 목록 조회", description = "관리자가 신고 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ReportListResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<ReportListResponse> findReports(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "마지막으로 조회한 신고 ID", required = false) @RequestParam(required = false) Long lastReportId
    );

    @Operation(summary = "용어 예문 수정", description = "지정한 용어의 예문 내용을 수정합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.WORD_EXAMPLE_NOT_FOUND
    })
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<Void> updateWordExample(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long wordId,
            @Parameter(description = "변경할 예문 ID") @PathVariable Long exampleId,
            UpdateWordExampleRequest request
    );

    @Operation(summary = "용어 예문 추가", description = "지정한 용어의 예문을 추가합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "403", description = "Forbidden - 인증된 사용자가 ADMIN 권한이 아닙니다.")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.WORD_EXAMPLE_NOT_FOUND
    })
    @Parameter(description = "관리자 권한 검증을 위해 요청 헤더에 'Authorization' 토큰을 포함해야 합니다.")
    ResponseEntity<Void> addWordExample(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long wordId,
            AddWordExampleRequest request
    );
}
