package kuit.springbasic.controller.qna;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class AnswerController {
    private final MemoryAnswerRepository answerRepository;
    private final MemoryQuestionRepository questionRepository;

    @RequestMapping("/addAnswer")
    public Answer addAnswer(@ModelAttribute Answer answer) {
        log.info("AnswerController.addAnswer");
        answerRepository.insert(answer);
        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);
        return answer;
    }

}
