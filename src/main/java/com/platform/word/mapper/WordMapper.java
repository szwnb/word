package com.platform.word.mapper;

import com.platform.word.entity.Word;
import com.platform.word.entity.WordVO; // 关键点：导入刚写好的 WordVO
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WordMapper {

    // 1. 获取今日要背的单词 (过滤掉已记住的)
    @Select("SELECT * FROM word WHERE book_id = #{bookId} AND id NOT IN " +
            "(SELECT word_id FROM user_word_record WHERE user_id = #{userId} AND status = 2) " +
            "ORDER BY RAND() LIMIT 20")
    List<Word> getWordsToStudy(@Param("bookId") Long bookId, @Param("userId") Long userId);

    // 2. 【保底功能】：获取带有用户个人记忆状态的单词列表
    @Select("SELECT w.id, w.word_name AS wordName, w.translation, " +
            "IFNULL(r.status, 0) AS status, IFNULL(r.wrong_count, 0) AS wrongCount " +
            "FROM word w " +
            "LEFT JOIN user_word_record r ON w.id = r.word_id AND r.user_id = #{userId} " +
            "WHERE w.book_id = #{bookId}")
    List<WordVO> getWordListWithStatus(@Param("bookId") Long bookId, @Param("userId") Long userId);

} // <--- 注意看，大括号在这里完美收尾！