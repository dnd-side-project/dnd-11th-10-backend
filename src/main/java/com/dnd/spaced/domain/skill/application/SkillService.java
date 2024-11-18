package com.dnd.spaced.domain.skill.application;

import com.dnd.spaced.domain.skill.domain.Category;
import com.dnd.spaced.domain.skill.domain.Skill;
import com.dnd.spaced.domain.skill.domain.repository.SkillRepository;
import com.dnd.spaced.domain.skill.presentation.dto.request.SkillRequest;
import com.dnd.spaced.domain.skill.presentation.dto.response.SkillTotalResponse;
import com.dnd.spaced.domain.skill.presentation.dto.response.SkillTotalScoreResponse;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    /**
     * 스킬 저장 로직
     *
     * @param info         유저 정보
     * @param skillRequest 카테고리와 맞춘 문제 개수
     */
    public void addSkill(AuthAccountInfo info, SkillRequest skillRequest) {
        Skill skill = findOrCreateSkill(info, skillRequest);
        skillRepository.save(skill);
    }

    /**
     * 유저의 카테고리별 스킬 점수를 계산 및 반환
     *
     * @param info 유저 정보
     * @return 카테고리별 스킬 점수 응답
     */
    public SkillTotalResponse getSkillTotalScore(AuthAccountInfo info) {
        Map<Category, SkillTotalScoreResponse> response = initializeCategoryScores();
        int totalSum = calculateCategoryScores(info, response);
        int totalAvgResponse = calculateAverageScore(response, totalSum);

        return SkillTotalResponse.builder()
                .totalAvgResponse(totalAvgResponse)
                .skillTotalScoreResponse(response)
                .build();
    }

    /**
     * 유저의 총점에 따라 상위 몇 퍼센트인지 계산
     *
     * @param info 유저 정보
     * @return 유저의 상위 퍼센트
     */
    public Long getTotalMyScore(AuthAccountInfo info) {
        List<Skill> skills = skillRepository.findByEmail(info.email());
        Long userTotalScore = skills.stream()
                .mapToLong(this::calculateTotalScore)
                .sum();

        Long averagePeopleScore = calculateAveragePeopleScore();

        return (averagePeopleScore == 0) ? 0 : (userTotalScore * 100) / averagePeopleScore;
    }

    /**
     * 스킬 저장 관련 메서드
     */
    private Skill findOrCreateSkill(AuthAccountInfo info, SkillRequest skillRequest) {
        Skill skill = skillRepository.findByCategoryAndEmail(skillRequest.category(), info.email());
        if (skill != null) {
            skill.updateSkill(skillRequest.correctCount());
            return skill;
        }
        return Skill.builder()
                .email(info.email())
                .correctCount(skillRequest.correctCount())
                .category(skillRequest.category())
                .totalCount(5L)
                .build();
    }

    /**
     * 카테고리 초기화 메서드
     */
    private Map<Category, SkillTotalScoreResponse> initializeCategoryScores() {
        Map<Category, SkillTotalScoreResponse> categoryScores = new HashMap<>();
        for (Category category : Category.values()) {
            if (category != Category.ALL) {
                categoryScores.put(category, SkillTotalScoreResponse.builder()
                        .totalScore(0L)
                        .currentCount(0L)
                        .totalCount(5L)
                        .build());
            }
        }
        return categoryScores;
    }

    /**
     * 카테고리별 점수 계산
     */
    private int calculateCategoryScores(AuthAccountInfo info, Map<Category, SkillTotalScoreResponse> response) {
        List<Skill> skills = skillRepository.findByEmail(info.email());
        int totalSum = 0;

        for (Skill skill : skills) {
            Long totalScore = calculateTotalScore(skill);
            totalSum += totalScore;
            response.put(skill.getCategory(), createSkillTotalScoreResponse(skill, totalScore));
        }
        return totalSum;
    }

    /**
     * 총 평균 점수 계산
     */
    private int calculateAverageScore(Map<Category, SkillTotalScoreResponse> response, int totalSum) {
        long validCategories = response.keySet().stream().filter(category -> category != Category.ALL).count();
        return validCategories == 0 ? 0 : totalSum / (int) validCategories;
    }

    /**
     * 개별 스킬의 정답 비율 계산
     */
    private Long calculateTotalScore(Skill skill) {
        return (skill.getCorrectCount() == 0) ? 0 : (skill.getCorrectCount() * 100) / skill.getTotalCount();
    }

    /**
     * 전체 유저 평균 점수 계산
     */
    private Long calculateAveragePeopleScore() {
        List<Skill> allSkills = skillRepository.findAll();
        Long totalPeopleScore = allSkills.stream()
                .mapToLong(Skill::getCorrectCount)
                .sum();
        return allSkills.isEmpty() ? 0 : totalPeopleScore / allSkills.size();
    }

    /**
     * 카테고리별 스킬 점수 생성
     */
    private SkillTotalScoreResponse createSkillTotalScoreResponse(Skill skill, Long totalScore) {
        return SkillTotalScoreResponse.builder()
                .totalScore(totalScore)
                .currentCount(skill.getCorrectCount())
                .totalCount(skill.getTotalCount())
                .build();
    }
}
