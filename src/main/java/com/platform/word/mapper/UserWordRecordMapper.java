package com.platform.word.mapper;

import com.platform.word.entity.UserWordRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserWordRecordMapper {

    // 1. 查一下这个人之前背没背过这个词
    @Select("SELECT * FROM user_word_record WHERE user_id = #{userId} AND word_id = #{wordId}")
    UserWordRecord getRecord(@Param("userId") Long userId, @Param("wordId") Long wordId);

    // 2. 如果是新词，就插入一条新记录
    @Insert("INSERT INTO user_word_record (user_id, word_id, status, wrong_count) VALUES (#{userId}, #{wordId}, #{status}, #{wrongCount})")
    void insertRecord(UserWordRecord record);

    // 3. 如果是背过的老词，就只更新它的状态和记错次数
    @Update("UPDATE user_word_record SET status = #{status}, wrong_count = #{wrongCount} WHERE id = #{id}")
    void updateRecord(UserWordRecord record);
}