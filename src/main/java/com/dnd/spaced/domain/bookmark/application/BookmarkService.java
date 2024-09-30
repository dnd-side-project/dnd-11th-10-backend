package com.dnd.spaced.domain.bookmark.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.bookmark.application.dto.BookmarkServiceMapper;
import com.dnd.spaced.domain.bookmark.application.dto.request.BookmarkConditionInfoDto;
import com.dnd.spaced.domain.bookmark.application.dto.response.BookmarkWordInfoDto;
import com.dnd.spaced.domain.bookmark.domain.Bookmark;
import com.dnd.spaced.domain.bookmark.domain.repository.BookmarkRepository;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.BookmarkRepositoryMapper;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.request.BookmarkConditionDto;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.response.BookmarkWordDto;
import com.dnd.spaced.domain.word.application.exception.ForbiddenBookmarkException;
import com.dnd.spaced.domain.word.application.exception.WordNotFoundException;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private static final int ADD_BOOKMARK_COUNT = 1;
    private static final int DELETE_BOOKMARK_COUNT = -1;

    private final WordRepository wordRepository;
    private final AccountRepository accountRepository;
    private final BookmarkRepository bookmarkRepository;

    public void processBookmark(String email, Long wordId) {
        Account account = findAccount(email);
        Word word = wordRepository.findBy(wordId).orElseThrow(WordNotFoundException::new);

        bookmarkRepository.findBy(account.getId(), word.getId())
                .ifPresentOrElse(
                        this::processDeleteBookmark,
                        () -> processAddBookmark(account.getId(), wordId)
                );
    }

    private void processAddBookmark(Long accountId, Long wordId) {
        Bookmark bookmark = Bookmark.builder()
                .accountId(accountId)
                .wordId(wordId)
                .build();

        bookmarkRepository.save(bookmark);
        wordRepository.updateBookmarkCount(bookmark.getWordId(), ADD_BOOKMARK_COUNT);
    }

    private void processDeleteBookmark(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
        wordRepository.updateBookmarkCount(bookmark.getWordId(), DELETE_BOOKMARK_COUNT);
    }

    public List<BookmarkWordInfoDto> findAllBy(BookmarkConditionInfoDto dto) {
        Account account = findAccount(dto.email());
        BookmarkConditionDto bookmarkConditionDto = BookmarkRepositoryMapper.of(
                account.getId(),
                dto.category(),
                dto.lastBookmarkId(),
                dto.pageable()
        );
        List<BookmarkWordDto> result = bookmarkRepository.findAllBy(bookmarkConditionDto);

        return BookmarkServiceMapper.from(result);
    }

    private Account findAccount(String email) {
        return accountRepository.findBy(email)
                .orElseThrow(ForbiddenBookmarkException::new);
    }
}
