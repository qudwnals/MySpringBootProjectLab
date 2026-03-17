package com.rookies5.myspringbootlab.book.repository;

import com.rookies5.myspringbootlab.book.domain.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {

    Optional<BookDetail> findByBookId(Long bookId);

    @Query("select bd from BookDetail bd join fetch bd.book where bd.id = :id")
    Optional<BookDetail> findByIdWithBook(Long id);

    List<BookDetail> findByPublisher(String publisher);
}
