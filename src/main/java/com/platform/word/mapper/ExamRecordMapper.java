package com.platform.word.mapper;

import com.platform.word.entity.ExamRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamRecordMapper {

    @Insert("INSERT INTO exam_record(user_id, book_id, score, total_count, correct_count) " +
            "VALUES(#{userId}, #{bookId}, #{score}, #{totalCount}, #{correctCount})")
    int insertRecord(ExamRecord record);
}