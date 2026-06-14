package com.platform.word.mapper;

import com.platform.word.entity.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WordMapper {

    // 核心算法：查出指定词汇书里，当前用户【还没记住】的单词
    @Select("SELECT * FROM word WHERE book_id = #{bookId} AND id NOT IN " +
            "(SELECT word_id FROM user_word_record WHERE user_id = #{userId} AND status = 2)")
    List<Word> getWordsToStudy(@Param("bookId") Long bookId, @Param("userId") Long userId);

}