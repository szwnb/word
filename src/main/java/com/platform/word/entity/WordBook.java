package com.platform.word.entity;

import lombok.Data;

@Data
public class WordBook {
    private Long id;
    private String bookName;
    private String description;

    private String targetAudience; // 适用人群
    private Integer wordCount;     // 总词汇量
}