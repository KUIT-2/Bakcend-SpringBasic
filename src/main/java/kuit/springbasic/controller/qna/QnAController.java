package kuit.springbasic.controller.qna;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;

    @GetMapping("/show")
    public String showQnA(@RequestParam int questionId, Model model) {
        log.info("QnAController.showQnA");
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);
        model.addAttribute("question", question).addAttribute("answers", answers);
        return "/qna/show";
    }
}
