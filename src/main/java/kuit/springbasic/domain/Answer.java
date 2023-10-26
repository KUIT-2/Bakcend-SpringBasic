package kuit.springbasic.domain;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

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

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return getQuestionId() == answer.getQuestionId() && Objects.equals(getWriter(), answer.getWriter()) && Objects.equals(getContents(), answer.getContents()) && Objects.equals(getCreatedDate(), answer.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionId(), getWriter(), getContents(), getCreatedDate());
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
