package com.platform.word.controller;

import com.platform.word.entity.Word;
import com.platform.word.mapper.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/word")
public class WordController {

    @Autowired
    private WordMapper wordMapper;

    // 获取今日要背的单词列表
    @GetMapping("/study")
    public List<Word> getStudyWords() {
        // 模拟：用户ID是1，要背的单词书ID是1
        return wordMapper.getWordsToStudy(1L, 1L);
    }
}