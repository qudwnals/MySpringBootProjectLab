package com.rookies5.myspringbootlab.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rookies5.myspringbootlab.book.domain.Book;
import com.rookies5.myspringbootlab.book.domain.BookDetail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "저자는 필수입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수입니다.")
        @Pattern(regexp = "^(?:\\d{10}|\\d{13})$", message = "ISBN은 10자리 또는 13자리 숫자여야 합니다.")
        private String isbn;

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 0, message = "가격은 음수일 수 없습니다.")
        private Integer price;

        @NotNull(message = "출간일은 필수입니다.")
        @PastOrPresent(message = "출간일은 오늘 이후일 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate publishDate;

        @NotNull(message = "상세 정보는 필수입니다.")
        @Valid
        private BookDetailDTO detailRequest;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PatchRequest {
        private String title;
        private String author;

        @Pattern(regexp = "^(?:\\d{10}|\\d{13})$", message = "ISBN은 10자리 또는 13자리 숫자여야 합니다.")
        private String isbn;

        @Min(value = 0, message = "가격은 음수일 수 없습니다.")
        private Integer price;

        @PastOrPresent(message = "출간일은 오늘 이후일 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate publishDate;

        @Valid
        private BookDetailPatchRequest detailRequest;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailDTO {
        @NotBlank(message = "설명은 필수입니다.")
        private String description;

        @NotBlank(message = "언어는 필수입니다.")
        private String language;

        @NotNull(message = "페이지 수는 필수입니다.")
        @Positive(message = "페이지 수는 1 이상이어야 합니다.")
        private Integer pageCount;

        @NotBlank(message = "출판사는 필수입니다.")
        private String publisher;

        @Size(max = 1000, message = "표지 이미지 URL은 1000자 이하여야 합니다.")
        private String coverImageUrl;

        private String edition;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BookDetailPatchRequest {
        private String description;
        private String language;

        @Positive(message = "페이지 수는 1 이상이어야 합니다.")
        private Integer pageCount;

        private String publisher;

        @Size(max = 1000, message = "표지 이미지 URL은 1000자 이하여야 합니다.")
        private String coverImageUrl;

        private String edition;
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
        private BookDetailResponse detailResponse;

        public static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .detailResponse(BookDetailResponse.from(book.getBookDetail()))
                    .build();
        }
    }

    @Getter
    @Builder
    public static class BookDetailResponse {
        private Long id;
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;

        public static BookDetailResponse from(BookDetail bookDetail) {
            if (bookDetail == null) {
                return null;
            }

            return BookDetailResponse.builder()
                    .id(bookDetail.getId())
                    .description(bookDetail.getDescription())
                    .language(bookDetail.getLanguage())
                    .pageCount(bookDetail.getPageCount())
                    .publisher(bookDetail.getPublisher())
                    .coverImageUrl(bookDetail.getCoverImageUrl())
                    .edition(bookDetail.getEdition())
                    .build();
        }
    }
}
