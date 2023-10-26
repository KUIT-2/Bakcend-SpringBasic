package kuit.springbasic.controller.v3;

import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class HomeControllerV2 implements ControllerV2 {

    private final MemoryQuestionRepository memoryQuestionRepository = MemoryQuestionRepository.getInstance();

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) {
        log.info("HomeControllerV2");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.put("questions", questions);
        return "/v3/v2/homeV2";
    }

}
