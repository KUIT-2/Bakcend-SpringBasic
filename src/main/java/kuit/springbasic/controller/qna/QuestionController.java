package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final MemoryQuestionRepository memoryQuestionRepository;


    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/qna/form")
    public String showQuestionForm() {
        log.info("QuestionController.showQuestionForm");

        return "/qna/form";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @RequestMapping("/qna/create")
    public String createQuestion(@RequestParam("writer") String writer, @RequestParam("title") String title, @RequestParam("contents") String contents) {
        log.info("QuestionController.createQuestion");

        Question question = new Question(writer, title, contents, 0);
        memoryQuestionRepository.insert(question);

        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @RequestMapping("/qna/updateForm")
    public String showUpdateQuestionForm(@RequestParam("questionId") String questionId, HttpServletRequest req, Model model) {
        log.info("QuestionController.showUpdateQuestionForm");

        HttpSession session = req.getSession();
        if(!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }

        Question question = memoryQuestionRepository.findByQuestionId(Integer.parseInt(questionId));
        if (!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/qna/update")
    public String updateQuestion(@RequestParam("questionId") String questionId, @RequestParam("title") String title, @RequestParam("contents") String contents, HttpServletRequest req) {
        log.info("QuestionController.updateQuestion");

        HttpSession session = req.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }

        Question question = memoryQuestionRepository.findByQuestionId(Integer.parseInt(questionId));

        if (!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            throw new IllegalArgumentException();
        }

        question.updateTitleAndContents(title, contents);
        memoryQuestionRepository.update(question);

        return "redirect:/";
    }
}
