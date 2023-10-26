package kuit.springbasic.controller.v1;

import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class HomeControllerV1 implements ControllerV1 {

    private final MemoryQuestionRepository memoryQuestionRepository = MemoryQuestionRepository.getInstance();

    @Override
    public ModelAndView execute(Map<String, String> params) {
        log.info("HomeControllerV1");

        ModelAndView modelAndView = new ModelAndView("/v1/homeV1");

        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.getModel().put("questions", questions);

        return modelAndView;
    }

}
