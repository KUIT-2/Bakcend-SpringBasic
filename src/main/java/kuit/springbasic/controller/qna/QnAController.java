package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnAController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;

    /**
     * TODO: showQnA
     */

    @RequestMapping("/qna/show")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("QnAController.showQuestionForm");

        int questionId = Integer.parseInt(request.getParameter("questionId"));

        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        request.setAttribute("question", question);

        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);
        request.setAttribute("answers", answers);

        return "qna/show";
    }


}
