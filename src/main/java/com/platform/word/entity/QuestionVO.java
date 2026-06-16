package com.platform.word.entity;

import java.util.List;

// 专门发给前端的“试题包装盒”
public class QuestionVO {
    private Long wordId;          // 正确单词的ID (用于判分)
    private String questionText;  // 题目题干 (比如中文翻译)
    private String correctAnswer; // 正确的英文单词 (可选，如果前端判分需要)
    private List<String> options; // 4个被打乱的选项 (A, B, C, D)

    // 快捷生成 Getter 和 Setter
    public Long getWordId() { return wordId; }
    public void setWordId(Long wordId) { this.wordId = wordId; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}