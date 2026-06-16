package com.platform.word.entity;

import java.util.Date;

public class ExamRecord {
    private Long id;
    private Long userId;
    private Long bookId;
    private Integer score;
    private Integer totalCount;
    private Integer correctCount;
    private Date examTime;

    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public Integer getTotalCount() { return totalCount; }
    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }
    public Integer getCorrectCount() { return correctCount; }
    public void setCorrectCount(Integer correctCount) { this.correctCount = correctCount; }
    public Date getExamTime() { return examTime; }
    public void setExamTime(Date examTime) { this.examTime = examTime; }
}