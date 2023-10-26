package kuit.springbasic.controller.v2.qna;

import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
public class ShowQnAControllerV2 implements ControllerV2 {

    private final MemoryQuestionRepository memoryQuestionRepository = MemoryQuestionRepository.getInstance();
    private final MemoryAnswerRepository memoryAnswerRepository = MemoryAnswerRepository.getInstance();

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) throws SQLException {
        log.info("ShowQnAControllerV2");

        int questionId = Integer.parseInt(params.get("questionId"));

        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);

        model.put("question", question);
        model.put("answers", answers);
        return "/v2/qna/show";
    }

}
