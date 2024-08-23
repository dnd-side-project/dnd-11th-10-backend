package com.dnd.spaced.domain.word.domain;

import com.dnd.spaced.domain.word.domain.exception.InvalidMeaningException;
import com.dnd.spaced.domain.word.domain.exception.InvalidNameException;
import com.dnd.spaced.domain.word.domain.exception.WordExampleNotFoundException;
import com.dnd.spaced.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class Word extends BaseTimeEntity {

    private static final int MIN_MEANING_LENGTH = 10;
    private static final int MAX_MEANING_LENGTH = 70;
    private static final int MIN_EXAMPLE_LENGTH = 1;
    private static final int MAX_EXAMPLE_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Pronunciation pronunciation;

    private String meaning;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(
            mappedBy = "word",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<WordExample> examples = new ArrayList<>();

    private int viewCount = 0;

    private int bookmarkCount = 0;

    private int commentCount = 0;

    @Builder
    private Word(
            String name,
            String englishPronunciation,
            String meaning,
            String categoryName,
            String example
    ) {
        validateContent(name, meaning);

        this.name = name;
        this.pronunciation = Pronunciation.builder()
                                          .english(englishPronunciation)
                                          .build();
        this.meaning = meaning;
        this.category = Category.findBy(categoryName);

        WordExample wordExample = WordExample.builder().content(example).build();

        wordExample.initWord(this);

        this.examples.add(wordExample);
    }

    public void updateWordExample(Long wordExampleId, String content) {
        WordExample target = this.examples.stream()
                                          .filter(wordExample -> wordExample.isEqualTo(wordExampleId))
                                          .findAny()
                                          .orElseThrow(WordExampleNotFoundException::new);

        target.updateContent(content);
    }

    public void addWordExample(String content) {
        WordExample wordExample = WordExample.builder().content(content).build();

        this.examples.add(wordExample);
    }

    public void addComment() {
        this.commentCount++;
    }

    public void deleteComment() {
        this.commentCount--;
    }

    private void validateContent(String name, String meaning) {
        if (isInvalidName(name)) {
            throw new InvalidNameException();
        }
        if (isInvalidMeaning(meaning)) {
            throw new InvalidMeaningException();
        }
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank();
    }

    private boolean isInvalidMeaning(String meaning) {
        int length = meaning.length();

        return MIN_MEANING_LENGTH > length || MAX_MEANING_LENGTH < length;
    }

    public void updateDetails(
            String name,
            String englishPronunciation,
            String meaning,
            String categoryName
    ) {
        this.name = name;
        this.pronunciation = Pronunciation.builder()
                                          .english(englishPronunciation)
                                          .build();
        this.meaning = meaning;
        this.category = Category.findBy(categoryName);
    }
}
