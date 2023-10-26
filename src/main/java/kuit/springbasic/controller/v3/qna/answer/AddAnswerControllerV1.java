package kuit.springbasic.controller.v3.qna.answer;

import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class AddAnswerControllerV1 implements ControllerV1 {

    private final MemoryAnswerRepository memoryAnswerRepository = MemoryAnswerRepository.getInstance();
    private final MemoryQuestionRepository memoryQuestionRepository = MemoryQuestionRepository.getInstance();

    @Override
    public ModelAndView execute(Map<String, String> params) throws SQLException {
        log.info("AddAnswerControllerV1");

        int questionId = Integer.parseInt(params.get("questionId"));
        String writer = params.get("writer");
        String contents = params.get("contents");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.getModel().put("answer", savedAnswer);
        return modelAndView;
    }

}
