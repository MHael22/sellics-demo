package com.diee.sellics.demo.dto;

public class EstimationDTO {

    private String keyword;
    private Integer score;

    public EstimationDTO(String keyword, Integer score) {
        this.keyword = keyword;
        this.score = score;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
