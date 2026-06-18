package com.platform.word.mapper;

import com.platform.word.entity.ExamRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface ExamRecordMapper {

    @Insert("INSERT INTO exam_record(user_id, book_id, score, total_count, correct_count) " +
            "VALUES(#{userId}, #{bookId}, #{score}, #{totalCount}, #{correctCount})")
    int insertRecord(ExamRecord record);

    // 查询用户的考试历史（连表查询出具体的书名）
    @Select("SELECT e.*, b.book_name as bookName " +
            "FROM exam_record e " +
            "LEFT JOIN word_book b ON e.book_id = b.id " +
            "WHERE e.user_id = #{userId} " +
            "ORDER BY e.exam_time DESC")
    List<ExamRecord> getHistoryByUserId(Long userId);
}