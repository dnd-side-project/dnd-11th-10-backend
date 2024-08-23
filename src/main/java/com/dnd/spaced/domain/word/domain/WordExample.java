package com.dnd.spaced.domain.word.domain;

import com.dnd.spaced.domain.word.domain.exception.InvalidExampleException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class WordExample {

    private static final int MIN_EXAMPLE_LENGTH = 1;
    private static final int MAX_EXAMPLE_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @Builder
    private WordExample(String content) {
        validateContent(content);

        this.content = content;
    }

    private void validateContent(String content) {
        if (isInvalidContent(content)) {
            throw new InvalidExampleException();
        }
    }

    private boolean isInvalidContent(String content) {
        int length = content.length();

        return MIN_EXAMPLE_LENGTH > length || MAX_EXAMPLE_LENGTH < length;
    }

    void initWord(Word word) {
        this.word = word;
    }

    public boolean isEqualTo(Long id) {
        return this.id.equals(id);
    }

    public void updateContent(String content) {
        validateContent(content);

        this.content = content;
    }
}
