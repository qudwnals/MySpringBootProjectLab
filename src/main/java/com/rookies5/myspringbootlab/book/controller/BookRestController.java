package com.rookies5.myspringbootlab.book.controller;

import com.rookies5.myspringbootlab.book.service.BookService;

/**
 * 실습2-2 문서의 클래스명(BookRestController) 요구사항을 맞추기 위한 호환 클래스입니다.
 */
public class BookRestController extends BookController {
    public BookRestController(BookService bookService) {
        super(bookService);
    }
}
