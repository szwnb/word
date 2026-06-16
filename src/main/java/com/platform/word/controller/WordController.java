package com.platform.word.controller;

import com.platform.word.entity.UserWordRecord;
import com.platform.word.entity.Word;
import com.platform.word.entity.WordVO; // 注意：这里导入了用来展示列表的 WordVO
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

    // 1. 获取今日要背的单词列表
    // 1. 获取今日要背的单词列表 (接收前端传来的书本ID)
    @GetMapping("/study")
    public List<Word> getStudyWords(@RequestParam Long bookId) {
        // userId 依然暂时写死为 1L，但 bookId 已经变成动态接收前端指令了！
        return wordMapper.getWordsToStudy(bookId, 1L);
    }

    // 2. 接收前端打卡：记录单词记忆状态
    @PostMapping("/record")
    public String recordWordStatus(@RequestBody Map<String, Integer> payload) {
        Long userId = 1L; // 依然写死当前用户ID为1
        Long wordId = payload.get("wordId").longValue();
        Integer status = payload.get("status");

        UserWordRecord record = new UserWordRecord();
        record.setUserId(userId);
        record.setWordId(wordId);
        record.setStatus(status);
        record.setWrongCount(status == 0 ? 1 : 0);

        recordMapper.saveOrUpdateRecord(record);

        return "后端结算完毕：单词 " + wordId + " 的状态已更新为 " + status;
    }

    // 3. 【全新保底功能】：获取特定单词书的进度列表 (千人千面)
    @GetMapping("/list")
    public List<WordVO> getBookWordList(@RequestParam Long bookId) {
        Long userId = 1L;
        return wordMapper.getWordListWithStatus(bookId, userId);
    }
}