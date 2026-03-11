package com.rookies5.myspringbootlab.book.repository;

import com.rookies5.myspringbootlab.book.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("도서 등록 테스트")
    void testCreateBook() {
        Book saved = bookRepository.save(sampleBook1());

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        bookRepository.save(sampleBook1());

        Book found = bookRepository.findByIsbn("9788956746425").orElseThrow();

        assertThat(found.getAuthor()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor() {
        bookRepository.save(sampleBook1());
        bookRepository.save(sampleBook2());

        List<Book> books = bookRepository.findByAuthor("홍길동");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getIsbn()).isEqualTo("9788956746425");
    }

    @Test
    @DisplayName("도서 정보 수정 테스트")
    void testUpdateBook() {
        Book saved = bookRepository.save(sampleBook1());

        saved.setTitle("스프링 부트 완전정복");
        saved.setPrice(33000);
        Book updated = bookRepository.save(saved);

        assertThat(updated.getTitle()).isEqualTo("스프링 부트 완전정복");
        assertThat(updated.getPrice()).isEqualTo(33000);
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        Book saved = bookRepository.save(sampleBook1());
        Long id = saved.getId();

        bookRepository.delete(saved);

        assertThat(bookRepository.findById(id)).isEmpty();
    }

    private Book sampleBook1() {
        return Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();
    }

    private Book sampleBook2() {
        return Book.builder()
                .title("JPA 프로그래밍")
                .author("박둘리")
                .isbn("9788956746432")
                .price(35000)
                .publishDate(LocalDate.of(2025, 4, 30))
                .build();
    }
}
