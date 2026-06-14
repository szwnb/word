package com.platform.word.entity;

import lombok.Data;

@Data
public class UserWordRecord {
    private Long id;
    private Long userId;
    private Long wordId;
    private Integer status;
    private Integer wrongCount;
}