package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerDAO;
import kuit.springbasic.db.QuestionDAO;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {

    private final AnswerDAO answerDAO;
    private final QuestionDAO questionDAO;
    /**
     * TODO: showQnA
     */
    @RequestMapping("/show")
    public String showQnA(@RequestParam int questionId, Model model) throws SQLException {
        log.info("QuestionController.showQnA");

        Question question = questionDAO.findByQuestionId(questionId);
        List<Answer> answers = answerDAO.findAllByQuestionId(questionId);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "/qna/show";
    }

}
