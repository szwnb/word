package com.platform.word.mapper;

import com.platform.word.entity.WordBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WordBookMapper {

    // 【修改点】：在查询语句中加入 target_audience 和 word_count，并做好别名映射
    @Select("SELECT id, book_name as bookName, description, " +
            "target_audience as targetAudience, word_count as wordCount " +
            "FROM word_book")
    List<WordBook> getAllBooks();
}