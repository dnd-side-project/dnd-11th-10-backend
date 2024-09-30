package com.dnd.spaced.domain.bookmark.domain.repository;

import static com.dnd.spaced.domain.bookmark.domain.QBookmark.bookmark;
import static com.dnd.spaced.domain.word.domain.QWord.word;

import com.dnd.spaced.domain.bookmark.domain.Bookmark;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.request.BookmarkConditionDto;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.response.BookmarkWordDto;
import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.exception.InvalidCategoryException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslBookmarkRepository implements BookmarkRepository {

    private static final String IGNORE_CATEGORY = "전체 실무";

    private final JPAQueryFactory queryFactory;
    private final BookmarkCrudRepository bookmarkCrudRepository;

    @Override
    public Bookmark save(Bookmark bookmark) {
        return bookmarkCrudRepository.save(bookmark);
    }

    @Override
    public void delete(Bookmark bookmark) {
        bookmarkCrudRepository.delete(bookmark);
    }

    @Override
    public Optional<Bookmark> findBy(Long accountId, Long wordId) {
        Bookmark result = queryFactory.selectFrom(bookmark)
                .where(
                        bookmark.accountId.eq(accountId),
                        bookmark.wordId.eq(wordId)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<BookmarkWordDto> findAllBy(BookmarkConditionDto dto) {
        return queryFactory.select(
                        Projections.constructor(
                                BookmarkWordDto.class,
                                word.id,
                                word.name,
                                word.pronunciation,
                                word.meaning,
                                word.category,
                                word.viewCount,
                                word.example,
                                word.createdAt,
                                word.updatedAt,
                                word.commentCount,
                                bookmark.id
                        )
                )
                .from(word)
                .leftJoin(bookmark).on(word.id.eq(bookmark.wordId), bookmark.accountId.eq(dto.accountId()))
                .where(lastBookmarkIdLt(dto.lastBookmarkId()))
                .where(
                        categoryEq(dto.category()),
                        lastBookmarkIdLt(dto.lastBookmarkId())
                )
                .orderBy(bookmark.id.desc())
                .limit(dto.pageable().getPageSize())
                .fetch();
    }

    private BooleanExpression lastBookmarkIdLt(Long lastBookmarkId) {
        if (lastBookmarkId == null) {
            return null;
        }

        return bookmark.id.lt(lastBookmarkId);
    }

    private BooleanExpression categoryEq(String category) {
        if (category == null || IGNORE_CATEGORY.equals(category)) {
            return null;
        }

        try {
            Category categoryEnum = Category.findBy(category);

            return word.category.eq(categoryEnum);
        } catch (InvalidCategoryException e) {
            return null;
        }
    }
}
