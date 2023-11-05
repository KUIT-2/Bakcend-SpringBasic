package kuit.springbasic.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Answer {
    private Long answerId;
    private Long questionId;
    private String writer;
    private String contents;
    private Date createdDate;


    public Answer(Long questionId, String writer, String contents) {
        this.questionId = questionId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
    }
}
