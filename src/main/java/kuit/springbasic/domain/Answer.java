package kuit.springbasic.domain;


import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Answer {
    private int answerId;
    private int questionId;
    private String writer;
    private String contents;
    private Date createdDate;

    public Answer(int questionId, String writer, String contents) {
        this.questionId = questionId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
    }
}
