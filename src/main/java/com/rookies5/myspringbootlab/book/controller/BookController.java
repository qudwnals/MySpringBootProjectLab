package com.rookies5.myspringbootlab.book.controller;

import com.rookies5.myspringbootlab.book.dto.BookDTO;
import com.rookies5.myspringbootlab.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO.BookResponse> createBook(@RequestBody @Valid BookDTO.BookCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO.BookResponse>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.BookResponse> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookDTO.BookUpdateRequest request
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
