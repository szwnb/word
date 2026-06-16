package com.platform.word.mapper;

import com.platform.word.entity.UserWordRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserWordRecordMapper {

    // 将以前的“先查、再插、或更新”三步，融合成这一步必杀技 (Upsert)
    @Insert("""
        INSERT INTO user_word_record (user_id, word_id, status, wrong_count)
        VALUES (#{userId}, #{wordId}, #{status}, #{wrongCount})
        ON DUPLICATE KEY UPDATE
        status = IF(status = 2, 2, VALUES(status)), 
        wrong_count = wrong_count + VALUES(wrong_count)
    """)
    void saveOrUpdateRecord(UserWordRecord record);

}