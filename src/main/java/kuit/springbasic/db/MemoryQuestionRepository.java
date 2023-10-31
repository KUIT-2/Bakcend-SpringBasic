package kuit.springbasic.db;


import kuit.springbasic.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository     //spring 컨테이너에 등록. 까보면 안에 @Component가 있음
// @repo, service, controller 등이 붙은 클래스들은 main함수 위에 붙은 springbootapplication로 인해 시작 시에 component scanning을 통해 컨테이너에 등록됨.
public class MemoryQuestionRepository {
    private Map<String, Question> questions= new HashMap<>();
    private static int PK = 0;

    public MemoryQuestionRepository() {
        insert(new Question("이주언","건대 어떻게 가나요?","홍대에서 건대 어떻게 가나요?",1));
        insert(new Question("강연주","서버 개잘하는데 질문받음","질문 ㄱ",0));

    }

    public static int getPK(){
        return ++PK;
    }

    public Question findByQuestionId(int id) {
        return questions.get(String.valueOf(id));
    }

    public void update(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.update(question);
    }

    public void insert(Question question) {
        question.setQuestionId(getPK());
        questions.put(Integer.toString(question.getQuestionId()), question);
    }

    public void updateCountOfAnswer(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.updateCountofAnswer(question);
    }

    public List<Question> findAll() {
        return questions.values().stream().toList();
    }
}
