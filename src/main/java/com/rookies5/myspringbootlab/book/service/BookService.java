package com.rookies5.myspringbootlab.book.service;

import com.rookies5.myspringbootlab.book.domain.Book;
import com.rookies5.myspringbootlab.book.dto.BookDTO;
import com.rookies5.myspringbootlab.book.repository.BookRepository;
import com.rookies5.myspringbootlab.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        return BookDTO.BookResponse.from(bookRepository.save(book));
    }

    public List<BookDTO.BookResponse> getBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }

    public BookDTO.BookResponse getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", "도서를 찾을 수 없습니다. id=" + id));
        return BookDTO.BookResponse.from(book);
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", "도서를 찾을 수 없습니다. isbn=" + isbn));
        return BookDTO.BookResponse.from(book);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", "도서를 찾을 수 없습니다. id=" + id));

        if (request.getTitle() != null) {
            existBook.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            existBook.setAuthor(request.getAuthor());
        }
        if (request.getIsbn() != null) {
            existBook.setIsbn(request.getIsbn());
        }
        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }

        return BookDTO.BookResponse.from(existBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", "도서를 찾을 수 없습니다. id=" + id));
        bookRepository.delete(book);
    }
}
