package com.platform.word.controller;

import com.platform.word.entity.WordBook;
import com.platform.word.mapper.WordBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private WordBookMapper bookMapper;

    @GetMapping("/list")
    public List<WordBook> getBookList() {
        return bookMapper.getAllBooks();
    }
}