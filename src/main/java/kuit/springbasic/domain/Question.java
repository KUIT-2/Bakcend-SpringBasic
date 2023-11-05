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
@EqualsAndHashCode(exclude = {"questionId"})
public class Question {
    private Long questionId;
    private String writer;
    private String title;
    private String contents;
    private Date createdDate;
    private Long countOfAnswer;

    public Question(String writer, String title, String contents, Long countOfAnswer) {
        this.questionId = 0L;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
        this.countOfAnswer = countOfAnswer;
    }

    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void increaseCountOfAnswer() {
        countOfAnswer += 1;
    }

    public void decreaseCountOfAnswer() {
        countOfAnswer -= 1;
    }
    public boolean isSameUser(User user) {
        return writer.equals(user.getUserId());
    }

    public void updateCountofAnswer(Question question){
        this.countOfAnswer = question.countOfAnswer;
    }
    public void update(Question question) {
        this.questionId = question.questionId;
        this.writer = question.writer;
        this.title = question.title;
        this.contents = question.contents;
        this.createdDate = question.createdDate;
        this.countOfAnswer = question.countOfAnswer;
    }
}
