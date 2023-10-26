package kuit.springbasic.controller.v1.qna.question;

import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class AddQuestionControllerV1 implements ControllerV1 {

    private final MemoryQuestionRepository memoryQuestionRepository = MemoryQuestionRepository.getInstance();

    @Override
    public ModelAndView execute(Map<String, String> params) throws SQLException {
        log.info("AddQuestionControllerV1");

        Question question = new Question(
                params.get("writer"),
                params.get("title"),
                params.get("contents"),
                0
        );
        memoryQuestionRepository.insert(question);

        return new ModelAndView("redirect:/v1");
    }

}
