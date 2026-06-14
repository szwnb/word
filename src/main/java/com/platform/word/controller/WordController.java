package com.platform.word.controller;

import com.platform.word.entity.UserWordRecord;
import com.platform.word.entity.Word;
import com.platform.word.mapper.UserWordRecordMapper;
import com.platform.word.mapper.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/word")
public class WordController {

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private UserWordRecordMapper recordMapper;

    // 获取今日要背的单词列表 (刚才写好的)
    @GetMapping("/study")
    public List<Word> getStudyWords() {
        return wordMapper.getWordsToStudy(1L, 1L); // 依然写死用户ID是1
    }

    // 【新加的核心逻辑】：更新单词记忆状态
    @PostMapping("/record")
    public String recordWordStatus(@RequestBody Map<String, Integer> payload) {
        Long userId = 1L; // 依然写死当前用户ID为1
        Long wordId = payload.get("wordId").longValue();
        Integer status = payload.get("status"); // 状态: 0未记住, 1模糊, 2已记住

        // 1. 先去数据库查有没有这个词的记录
        UserWordRecord record = recordMapper.getRecord(userId, wordId);

        if (record == null) {
            // 2. 没有记录说明是第一次背，新建一个
            record = new UserWordRecord();
            record.setUserId(userId);
            record.setWordId(wordId);
            record.setStatus(status);
            record.setWrongCount(status == 0 ? 1 : 0); // 如果选了不认识(0)，记错次数直接记1
            recordMapper.insertRecord(record);
        } else {
            // 3. 有记录说明以前背过，更新状态
            record.setStatus(status);
            if (status == 0) {
                record.setWrongCount(record.getWrongCount() + 1); // 只要点了不认识，错误次数累计+1
            }
            recordMapper.updateRecord(record);
        }

        return "后端结算完毕：单词 " + wordId + " 的状态已更新为 " + status;
    }
}