package kuit.springbasic.controller.qna;

import kuit.springbasic.dao.AnswerDao;
import kuit.springbasic.dao.QuestionDao;
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
//    private final MemoryAnswerRepository answerRepository;
//    private final MemoryQuestionRepository questionRepository;
    private final AnswerDao answerDao;
    private final QuestionDao questionDao;

    @RequestMapping("/addAnswer")
    public Answer addAnswer(@ModelAttribute Answer answer) {
        log.info("AnswerController.addAnswer");
        answerDao.insert(answer);
        Question question = questionDao.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionDao.updateCountOfAnswer(question);
        return answer;
    }

}
