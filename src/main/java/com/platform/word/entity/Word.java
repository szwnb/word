package com.platform.word.entity;

import lombok.Data;

@Data
public class Word {
    private Long id;
    private Long bookId;
    private String wordName;
    private String translation;
}