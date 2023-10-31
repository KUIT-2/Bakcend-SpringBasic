package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
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
    //여기서 response를 어떻게 사용해야할지 잘 모르겠습니다!
    @PostMapping("/api/qna/addAnswer")
    public ModelAndView addAnswerV0(@RequestParam int questionId, @RequestParam String writer,
            @RequestParam String contents, HttpServletResponse response) {
        Answer answer = new Answer(memoryAnswerRepository.getPK(), questionId, writer, contents, Date.valueOf(LocalDate.now()));
        memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        response.setContentType("application/json;charset=UTF-8");

        return new ModelAndView().addObject("answer", answer);
    }

//     addAnswerV1 : @RequestParam, Model

    public ModelAndView addAnswerV1(@RequestParam int questionId, @RequestParam String writer,
            @RequestParam String contents, Model model) {
        Answer answer = new Answer(memoryAnswerRepository.getPK(), questionId, writer, contents, Date.valueOf(LocalDate.now()));
        memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);
        model.addAttribute("answer", answer);
        return new ModelAndView();
    }
//     addAnswerV2 : @RequestParam, @ResponseBody
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
