package kuit.springbasic.controller.v2.qna.question;

import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class AddQuestionControllerV2 implements ControllerV2 {

    private final MemoryQuestionRepository memoryQuestionRepository = MemoryQuestionRepository.getInstance();

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) throws SQLException {
        log.info("AddQuestionControllerV2");

        Question question = new Question(
                params.get("writer"),
                params.get("title"),
                params.get("contents"),
                0
        );
        memoryQuestionRepository.insert(question);

        return "redirect:/v2";
    }

}
