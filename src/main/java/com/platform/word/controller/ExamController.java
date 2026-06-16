package com.platform.word.controller;

import com.platform.word.entity.QuestionVO;
import com.platform.word.entity.Word;
import com.platform.word.mapper.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    @Autowired
    private WordMapper wordMapper;

    // 生成随机自测卷 (看中文，选英文)
    // 生成随机自测卷 (看中文，选英文)
    @GetMapping("/generate")
    public List<QuestionVO> generateExam(@RequestParam Long bookId, @RequestParam Integer count) {
        // 1. 把这本书所有的单词捞到内存里
        List<Word> allWords = wordMapper.getAllWordsByBookId(bookId);

        // 防御性判断：如果这本书本来就没单词，直接交白卷
        if (allWords == null || allWords.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 打乱所有单词，取出必考题
        List<Word> shuffledList = new ArrayList<>(allWords);
        Collections.shuffle(shuffledList);
        List<Word> targetWords = shuffledList.subList(0, Math.min(count, shuffledList.size()));

        List<QuestionVO> examPaper = new ArrayList<>();

        // 3. 为每一道题生成干扰错项 (自适应版)
        for (Word target : targetWords) {
            QuestionVO question = new QuestionVO();
            question.setWordId(target.getId());
            question.setQuestionText(target.getTranslation());
            question.setCorrectAnswer(target.getWordName());

            // 挑错项：把除了正确答案以外的词放进错词池，并洗牌
            List<Word> wrongPool = new ArrayList<>(allWords);
            wrongPool.removeIf(w -> w.getId().equals(target.getId()));
            Collections.shuffle(wrongPool);

            List<String> options = new ArrayList<>();
            options.add(target.getWordName()); // 塞入 1 个正确答案

            // 动态塞入错项：最多塞 3 个，如果池子里不够 3 个，有几个塞几个
            int wrongCount = Math.min(3, wrongPool.size());
            for (int i = 0; i < wrongCount; i++) {
                options.add(wrongPool.get(i).getWordName());
            }

            // 彻底打乱选项，防止正确答案永远是第一个
            Collections.shuffle(options);
            question.setOptions(options);

            examPaper.add(question);
        }

        return examPaper;
    }
}