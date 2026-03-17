package com.rookies5.myspringbootlab.book.service;

import com.rookies5.myspringbootlab.book.domain.Book;
import com.rookies5.myspringbootlab.book.domain.BookDetail;
import com.rookies5.myspringbootlab.book.dto.BookDTO;
import com.rookies5.myspringbootlab.book.repository.BookRepository;
import com.rookies5.myspringbootlab.common.exception.BusinessException;
import com.rookies5.myspringbootlab.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.Request request) {
        validateDuplicateIsbn(request.getIsbn());

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        book.assignDetail(createBookDetail(request.getDetailRequest()));

        return BookDTO.BookResponse.from(bookRepository.save(book));
    }

    public List<BookDTO.BookResponse> getBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }

    public BookDTO.BookResponse getBook(Long id) {
        return BookDTO.BookResponse.from(findBookWithDetailById(id));
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, isbn));
        return BookDTO.BookResponse.from(book);
    }

    public List<BookDTO.BookResponse> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }

    public List<BookDTO.BookResponse> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.Request request) {
        Book book = findBookWithDetailById(id);

        validateDuplicateIsbnOnChange(book, request.getIsbn());

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        updateBookDetail(book.getBookDetail(), request.getDetailRequest());

        return BookDTO.BookResponse.from(book);
    }

    @Transactional
    public BookDTO.BookResponse patchBook(Long id, BookDTO.PatchRequest request) {
        Book book = findBookWithDetailById(id);

        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getIsbn() != null) {
            validateDuplicateIsbnOnChange(book, request.getIsbn());
            book.setIsbn(request.getIsbn());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(request.getPublishDate());
        }
        if (request.getDetailRequest() != null) {
            patchBookDetailFields(book.getBookDetail(), request.getDetailRequest());
        }

        return BookDTO.BookResponse.from(book);
    }

    @Transactional
    public BookDTO.BookResponse patchBookDetail(Long id, BookDTO.BookDetailPatchRequest request) {
        Book book = findBookWithDetailById(id);
        patchBookDetailFields(book.getBookDetail(), request);
        return BookDTO.BookResponse.from(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = findBookWithDetailById(id);
        bookRepository.delete(book);
    }

    private Book findBookWithDetailById(Long id) {
        return bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, id));
    }

    private void validateDuplicateIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, isbn);
        }
    }

    private void validateDuplicateIsbnOnChange(Book book, String isbn) {
        if (!book.getIsbn().equals(isbn) && bookRepository.existsByIsbn(isbn)) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, isbn);
        }
    }

    private BookDetail createBookDetail(BookDTO.BookDetailDTO request) {
        return BookDetail.builder()
                .description(request.getDescription())
                .language(request.getLanguage())
                .pageCount(request.getPageCount())
                .publisher(request.getPublisher())
                .coverImageUrl(request.getCoverImageUrl())
                .edition(request.getEdition())
                .build();
    }

    private void updateBookDetail(BookDetail bookDetail, BookDTO.BookDetailDTO request) {
        bookDetail.setDescription(request.getDescription());
        bookDetail.setLanguage(request.getLanguage());
        bookDetail.setPageCount(request.getPageCount());
        bookDetail.setPublisher(request.getPublisher());
        bookDetail.setCoverImageUrl(request.getCoverImageUrl());
        bookDetail.setEdition(request.getEdition());
    }

    private void patchBookDetailFields(BookDetail bookDetail, BookDTO.BookDetailPatchRequest request) {
        if (request.getDescription() != null) {
            bookDetail.setDescription(request.getDescription());
        }
        if (request.getLanguage() != null) {
            bookDetail.setLanguage(request.getLanguage());
        }
        if (request.getPageCount() != null) {
            bookDetail.setPageCount(request.getPageCount());
        }
        if (request.getPublisher() != null) {
            bookDetail.setPublisher(request.getPublisher());
        }
        if (request.getCoverImageUrl() != null) {
            bookDetail.setCoverImageUrl(request.getCoverImageUrl());
        }
        if (request.getEdition() != null) {
            bookDetail.setEdition(request.getEdition());
        }
    }
}
