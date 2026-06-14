package com.platform.word.mapper;

import com.platform.word.entity.WordBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WordBookMapper {
    @Select("SELECT id, book_name as bookName, description FROM word_book")
    List<WordBook> getAllBooks();
}