package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;
    /**
     * TODO: showQnA
     */
    @GetMapping("/show")
    public String showQnA(HttpServletRequest request, @RequestParam int questionId) {
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);
        request.setAttribute("question", question);
        request.setAttribute("answers", answers);
        return "qna/show";
    }
}
