package com.dnd.spaced.domain.admin.application;

import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.word.application.exception.WordNotFoundException;
import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final WordRepository wordRepository;

    @Transactional
    public Long createWord(AdminWordRequestDto wordRequestDto) {
        Word word = AdminServiceMapper.fromCreateRequest(wordRequestDto);
        wordRepository.save(word);
        return word.getId();
    }

    @Transactional
    public void deleteWord(Long wordId) {
        Word word = findWordById(wordId);
        wordRepository.delete(word);
    }

    @Transactional
    public void updateWord(Long wordId, AdminWordRequestDto wordRequestDto) {
        Word word = Word.builder()
                .name(wordRequestDto.name())
                .englishPronunciation(wordRequestDto.pronunciation().getEnglish())
                .meaning(wordRequestDto.meaning())
                .categoryName(String.valueOf(Category.findBy(wordRequestDto.category())))
                .example(wordRequestDto.example())
                .build();

        wordRepository.save(word);
    }

    public AdminWordResponse getWord(Long wordId) {
        Word word = findWordById(wordId);
        return AdminServiceMapper.toResponseDto(word);
    }

    private Word findWordById(Long wordId) {
        return wordRepository.findBy(wordId)
                .orElseThrow(WordNotFoundException::new);
    }
}