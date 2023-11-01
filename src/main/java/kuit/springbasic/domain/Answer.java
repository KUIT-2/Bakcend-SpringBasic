package kuit.springbasic.domain;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
public class Answer {
    private int answerId;
    private int questionId;
    private String writer;
    private String contents;
    private Date createdDate;

    public Answer() {

    }

    public Answer(int answerId, int questionId, String writer, String contents, Date createdDate) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public Answer(int questionId, String writer, String contents) {
        this.questionId = questionId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
    }


    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
