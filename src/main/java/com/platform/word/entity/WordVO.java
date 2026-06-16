package com.platform.word.entity;

// 专门用于前端列表展示的合并对象
public class WordVO {
    private Long id;
    private String wordName;     // 英文
    private String translation;  // 中文
    private Integer status;      // 状态：0未背/不记得, 1模糊, 2已记住
    private Integer wrongCount;  // 记错次数

    // 快捷生成 Getter 和 Setter (如果在用 IDEA，按 Alt+Insert 快速生成)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWordName() { return wordName; }
    public void setWordName(String wordName) { this.wordName = wordName; }
    public String getTranslation() { return translation; }
    public void setTranslation(String translation) { this.translation = translation; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getWrongCount() { return wrongCount; }
    public void setWrongCount(Integer wrongCount) { this.wrongCount = wrongCount; }
}