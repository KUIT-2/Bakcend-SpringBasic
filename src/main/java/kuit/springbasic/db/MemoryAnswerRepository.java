package kuit.springbasic.db;


import kuit.springbasic.domain.Answer;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemoryAnswerRepository {

    private Map<String, Answer> answers= new HashMap<>();
    private  static int PK = 0;

    private static MemoryAnswerRepository memoryAnswerRepository;

    private MemoryAnswerRepository() {
        insert(new Answer(1,"함형주","7호선 타세요"));
    }

    public static MemoryAnswerRepository getInstance() {
        if (memoryAnswerRepository == null) {
            memoryAnswerRepository = new MemoryAnswerRepository();
            return memoryAnswerRepository;
        }
        return memoryAnswerRepository;
    }

    public int getPK(){
        return ++PK;
    }
    public List<Answer> findAllByQuestionId(int id) {
        ArrayList<Answer> result = new ArrayList<>();

        Set<String> answerKeys = answers.keySet();
        for (String key : answerKeys) {
            Answer repoAnswer = answers.get(key);
            if(repoAnswer.getQuestionId() == id){
                result.add(repoAnswer);
            }
        }

        return result;
    }


    public Answer insert(Answer answer) {
        answer.setAnswerId(getPK());
        answer.setCreatedDate(Date.valueOf(LocalDate.now()));
        answers.put(Integer.toString(answer.getAnswerId()),answer);
        return answer;
    }
}
