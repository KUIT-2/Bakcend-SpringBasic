package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnAController {

    @Autowired
    private MemoryQuestionRepository memoryQuestionRepository;

    @Autowired
    private MemoryAnswerRepository memoryAnswerRepository;

    /**
     * TODO: showQnA
     */
    @GetMapping("/showQnA")
    public String showQnA() {
        log.info("showQnA");
        return "qna/show";
    }


    //    경로 show
    @GetMapping("qna/show")
    public ModelAndView showQuestionDetail(@RequestParam Integer questionId,
                                           HttpServletRequest request,
                                           Model model) {
        log.info("showUpdateQuestionForm");

        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);
        request.setAttribute("question", question);
        request.setAttribute("answers",answers);
        ModelAndView modelAndView = new ModelAndView("qna/show");

        return modelAndView;
    }


}
