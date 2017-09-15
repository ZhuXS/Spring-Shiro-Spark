package com.zhuxs.result.dto;

/**
 * Created by shusesshou on 2017/9/11.
 */
public class TextDto {
    private String words;

    public TextDto() {
    }

    public TextDto(String words) {
        this.words = words;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextDto TextDto = (TextDto) o;

        return words != null ? words.equals(TextDto.words) : TextDto.words == null;
    }

    @Override
    public int hashCode() {
        return words != null ? words.hashCode() : 0;
    }
}
