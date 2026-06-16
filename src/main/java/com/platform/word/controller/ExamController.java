package com.platform.word.controller;

import com.platform.word.entity.QuestionVO;
import com.platform.word.entity.Word;
import com.platform.word.entity.ExamRecord;
import com.platform.word.mapper.WordMapper;
import com.platform.word.mapper.ExamRecordMapper;
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

    @Autowired
    private ExamRecordMapper examRecordMapper; // 注入新 Mapper

    // 生成随机自测卷 (保持不变)
    @GetMapping("/generate")
    public List<QuestionVO> generateExam(
            @RequestParam Long bookId,
            @RequestParam Integer count,
            @RequestParam(defaultValue = "0") Integer reverseRatio
    ) {
        List<Word> allWords = wordMapper.getAllWordsByBookId(bookId);
        if (allWords == null || allWords.isEmpty()) return new ArrayList<>();

        List<Word> shuffledList = new ArrayList<>(allWords);
        Collections.shuffle(shuffledList);
        List<Word> targetWords = shuffledList.subList(0, Math.min(count, shuffledList.size()));

        List<QuestionVO> examPaper = new ArrayList<>();
        java.util.Random random = new java.util.Random();

        for (Word target : targetWords) {
            QuestionVO question = new QuestionVO();
            question.setWordId(target.getId());

            boolean isReverse = random.nextInt(100) < reverseRatio;
            if (isReverse) {
                question.setQuestionText(target.getWordName());
                question.setCorrectAnswer(target.getTranslation());
            } else {
                question.setQuestionText(target.getTranslation());
                question.setCorrectAnswer(target.getWordName());
            }

            List<Word> wrongPool = new ArrayList<>(allWords);
            wrongPool.removeIf(w -> w.getId().equals(target.getId()));
            Collections.shuffle(wrongPool);

            List<String> options = new ArrayList<>();
            options.add(question.getCorrectAnswer());

            int wrongCount = Math.min(3, wrongPool.size());
            for (int i = 0; i < wrongCount; i++) {
                String wrongOption = isReverse ? wrongPool.get(i).getTranslation() : wrongPool.get(i).getWordName();
                options.add(wrongOption);
            }

            Collections.shuffle(options);
            question.setOptions(options);
            examPaper.add(question);
        }
        return examPaper;
    }

    // 【新增】：接收前端提交的考试成绩并落库
    @PostMapping("/submit")
    public String submitExam(@RequestBody ExamRecord record) {
        record.setUserId(1L); // 依然先写死当前用户为 1L
        examRecordMapper.insertRecord(record);
        return "成绩已成功记录到数据库！生成记录ID：" + record.getId();
    }
}