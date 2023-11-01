package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

    private final MemoryQuestionRepository memoryQuestionRepository;

    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/qna/form")
    public String showQuestionForm() {
        log.info("QuestionController.showQuestionForm");

        return "qna/form";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @RequestMapping("/qna/create")
    public String createQuestion(HttpSession session, @RequestParam String title, @RequestParam String contents) {
        log.info("QuestionController.createQuestion");
        User user = (User) session.getAttribute("user");

        Question question = new Question(user.getName(), title, contents, 0);
        memoryQuestionRepository.insert(question);


        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @RequestMapping("/qna/updateForm")
    public String showUpdateQuestionForm() {
        log.info("QuestionController.showUpdateQuestionForm");

        return "qna/updateForm";
    }



    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/qna/update")
    public String updateQuestion(HttpServletRequest request, @RequestParam String title, @RequestParam String contents) {
        log.info("QuestionController.updateQuestion");

        int questionId = Integer.parseInt(request.getParameter("questionId"));
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        question.updateTitleAndContents(title, contents);

        return "redirect:/";
    }
}
