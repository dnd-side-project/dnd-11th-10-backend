package com.dnd.spaced.domain.admin.presentation;

import com.dnd.spaced.domain.admin.application.AdminService;
import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.application.dto.response.ReportInfoDto;
import com.dnd.spaced.domain.admin.presentation.dto.AdminControllerMapper;
import com.dnd.spaced.domain.admin.presentation.dto.request.AddWordExampleRequest;
import com.dnd.spaced.domain.admin.presentation.dto.request.UpdateWordExampleRequest;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.admin.presentation.dto.response.ReportListResponse;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController implements SwaggerAdminController {

    private final AdminService adminService;

    @PostMapping("/words")
    public ResponseEntity<Void> createWord(Authentication authentication,
                                           @Valid @RequestBody AdminWordRequestDto wordRequestDto) {
        Long wordId = adminService.createWord(wordRequestDto);
        return ResponseEntity.created(URI.create("/words/" + wordId)).build();
    }

    @DeleteMapping("/words/{id}")
    public ResponseEntity<Void> deleteWord(Authentication authentication,
                                           @PathVariable Long id) {
        adminService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/words/{id}")
    public ResponseEntity<Void> updateWord(Authentication authentication,
                                           @PathVariable Long id,
                                           @Valid @RequestBody AdminWordRequestDto wordRequestDto) {
        adminService.updateWord(id, wordRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/words/{id}")
    public ResponseEntity<AdminWordResponse> getWord(@PathVariable Long id) {
        AdminWordResponse wordResponseDto = adminService.getWord(id);
        return ResponseEntity.ok(wordResponseDto);
    }

    @PostMapping("/reports/{id}/accept")
    public ResponseEntity<Void> acceptReport(@PathVariable Long id) {
        adminService.acceptReport(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reports/{id}/ignore")
    public ResponseEntity<Void> ignoreReport(@PathVariable Long id) {
        adminService.ignoreReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reports")
    public ResponseEntity<ReportListResponse> findReports(@RequestParam(required = false) Long lastReportId) {
        List<ReportInfoDto> reports = adminService.findReports(lastReportId);

        ReportListResponse response = AdminControllerMapper.toReportListResponse(reports, lastReportId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/words/{wordId}/examples/{exampleId}")
    public ResponseEntity<Void> updateWordExample(
            @PathVariable Long wordId,
            @PathVariable Long exampleId,
            @Valid @RequestBody UpdateWordExampleRequest request
    ) {
        adminService.updateWordExample(wordId, exampleId, request.content());

        return ResponseEntityConst.NO_CONTENT;
    }

    @PostMapping("/words/{wordId}/examples")
    public ResponseEntity<Void> addWordExample(
            @PathVariable Long wordId,
            @Valid @RequestBody AddWordExampleRequest request
    ) {
        adminService.addWordExample(wordId, request.content());

        return ResponseEntityConst.NO_CONTENT;
    }
}
