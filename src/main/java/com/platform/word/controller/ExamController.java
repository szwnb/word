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
    // 生成随机自测卷 (支持动态比例：看中文选英文 vs 看英文选中文)
    @GetMapping("/generate")
    public List<QuestionVO> generateExam(
            @RequestParam Long bookId,
            @RequestParam Integer count,
            @RequestParam(defaultValue = "0") Integer reverseRatio // 新增参数：英翻中的百分比(0-100)
    ) {
        List<Word> allWords = wordMapper.getAllWordsByBookId(bookId);
        if (allWords == null || allWords.isEmpty()) return new ArrayList<>();

        List<Word> shuffledList = new ArrayList<>(allWords);
        Collections.shuffle(shuffledList);
        List<Word> targetWords = shuffledList.subList(0, Math.min(count, shuffledList.size()));

        List<QuestionVO> examPaper = new ArrayList<>();
        java.util.Random random = new java.util.Random(); // 引入随机数发生器

        for (Word target : targetWords) {
            QuestionVO question = new QuestionVO();
            question.setWordId(target.getId());

            // 核心算法：扔骰子决定这道题是什么题型
            // 比如 reverseRatio 是 30，那么随机数小于 30 的概率就是 30%
            boolean isReverse = random.nextInt(100) < reverseRatio;

            if (isReverse) {
                // 题型A：看英文，选中文
                question.setQuestionText(target.getWordName());
                question.setCorrectAnswer(target.getTranslation());
            } else {
                // 题型B：看中文，选英文 (原本的逻辑)
                question.setQuestionText(target.getTranslation());
                question.setCorrectAnswer(target.getWordName());
            }

            // 挑错项池
            List<Word> wrongPool = new ArrayList<>(allWords);
            wrongPool.removeIf(w -> w.getId().equals(target.getId()));
            Collections.shuffle(wrongPool);

            List<String> options = new ArrayList<>();
            options.add(question.getCorrectAnswer()); // 塞入正确答案

            int wrongCount = Math.min(3, wrongPool.size());
            for (int i = 0; i < wrongCount; i++) {
                // 关键点：如果是看英文选中文，干扰项也必须是中文！
                String wrongOption = isReverse ? wrongPool.get(i).getTranslation() : wrongPool.get(i).getWordName();
                options.add(wrongOption);
            }

            Collections.shuffle(options);
            question.setOptions(options);
            examPaper.add(question);
        }

        return examPaper;
    }
}