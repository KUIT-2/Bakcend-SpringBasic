package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final MemoryAnswerRepository memoryAnswerRepository;
    private final MemoryQuestionRepository memoryQuestionRepository;

    //     TODO: addAnswer - @PostMapping
//     addAnswerV0 : @RequestParam, HttpServletResponse
    //여기서 response를 어떻게 사용해야할지 잘 모르겠습니다! -> 5주차의 JsonView의 render를 수행하면 되는구나..

    public void addAnswerV0(@RequestParam int questionId, @RequestParam String writer,
            @RequestParam String contents, HttpServletResponse response) throws IOException {
        log.info("AnswerController.addAnswerV0");

        Answer answer = new Answer(memoryAnswerRepository.getPK(), questionId, writer, contents,
                Date.valueOf(LocalDate.now()));
        Answer savedAnswer = memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        //JsonView render와 동일. print와 write의 차이가 있다
        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }

//     addAnswerV1 : @RequestParam, Model

    public String addAnswerV1(@RequestParam int questionId, @RequestParam String writer,
            @RequestParam String contents, Model model) {
        Answer answer = new Answer(memoryAnswerRepository.getPK(), questionId, writer, contents, Date.valueOf(LocalDate.now()));
        memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);
        model.addAttribute("answer", answer);
//        return new ModelAndView();
        return "jsonView"; //WebConfig 라는 게 있구나!
    }
//     addAnswerV2 : @RequestParam, @ResponseBody
    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Answer addAnswerV2(@RequestParam int questionId, @RequestParam String writer,
            @RequestParam String contents) {
        Answer answer = new Answer(memoryAnswerRepository.getPK(), questionId, writer, contents, Date.valueOf(LocalDate.now()));
        memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        return answer;
    }

//     addAnswerV3 : @ModelAttribute, @ResponseBody

    @ResponseBody
    public Answer addAnswerV3(@ModelAttribute Answer answer) {
        memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        return answer;
    }

}
