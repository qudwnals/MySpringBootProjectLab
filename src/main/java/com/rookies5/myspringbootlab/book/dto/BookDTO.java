package com.rookies5.myspringbootlab.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rookies5.myspringbootlab.book.domain.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookCreateRequest {
        @NotBlank(message = "title은 필수입니다.")
        private String title;

        @NotBlank(message = "author는 필수입니다.")
        private String author;

        @NotBlank(message = "isbn은 필수입니다.")
        private String isbn;

        @NotNull(message = "price는 필수입니다.")
        @Min(value = 1, message = "price는 1 이상이어야 합니다.")
        private Integer price;

        @NotNull(message = "publishDate는 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate publishDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookUpdateRequest {
        private String title;
        private String author;
        private String isbn;

        @Min(value = 1, message = "price는 1 이상이어야 합니다.")
        private Integer price;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate publishDate;
    }

    @Getter
    @Builder
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .build();
        }
    }
}
