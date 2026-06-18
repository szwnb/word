package com.platform.word.controller;

import com.platform.word.entity.QuestionVO;
import com.platform.word.entity.Word;
import com.platform.word.entity.ExamRecord;
import com.platform.word.entity.User; // 【新增】导入 User 实体
import com.platform.word.mapper.WordMapper;
import com.platform.word.mapper.ExamRecordMapper;
import com.platform.word.mapper.UserMapper; // 【新增】导入 UserMapper
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
    private ExamRecordMapper examRecordMapper;

    // 【新增】：注入 UserMapper，用来根据用户名查真实的 ID
    @Autowired
    private UserMapper userMapper;

    // 生成随机自测卷 (保持完美，一行没动)
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

    // 【修复点 1】：交卷时，接收前端传来的 username，查出真实 ID 再存入数据库
    @PostMapping("/submit")
    public String submitExam(@RequestBody ExamRecord record, @RequestParam String username) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            record.setUserId(user.getId()); // 替换掉原来的 1L
        }
        examRecordMapper.insertRecord(record);
        return "成绩已成功记录到数据库！生成记录ID：" + record.getId();
    }

    // 【修复点 2】：查历史时，接收前端传来的 username，查出真实 ID 再去查成绩
    @GetMapping("/history")
    public List<ExamRecord> getHistory(@RequestParam String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return Collections.emptyList(); // 如果没查到人，返回空列表防崩溃
        }
        return examRecordMapper.getHistoryByUserId(user.getId()); // 替换掉原来的 1L
    }
}