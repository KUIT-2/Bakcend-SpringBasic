package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnAController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;
//     TODO: showQnA

    @GetMapping("/qna/show")
    public ModelAndView showQnA(HttpServletRequest request, Model model) {
         log.info("QnAController.showQnA");

        String questionId = request.getParameter("questionId");
        Question question = memoryQuestionRepository.findByQuestionId(Integer.parseInt(questionId));
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(Integer.parseInt(questionId));
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return new ModelAndView("/qna/show");
    }
}
