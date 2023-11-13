package kuit.springbasic.domain;


import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class Answer {
    private int answerId;
    private int questionId;
    private String writer;
    private String contents;
    private Date createdDate;

    public Answer() {

    }

    public Answer(int questionId, String writer, String contents) {
        this.questionId = questionId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
    }

}
