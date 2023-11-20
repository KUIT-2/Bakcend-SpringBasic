package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.dao.QuestionDao;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionDao questionDao;

    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("QuestionController.showQuestionForm");

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    //    @RequestMapping("/create")
    public String createQuestionV1(@RequestParam String writer, @RequestParam String title, @RequestParam String contents) throws SQLException {
        log.info("QuestionController.createQuestionV1");

        Question question = new Question(writer, title, contents, 0L);
        questionDao.insert(question);

        return "redirect:/";
    }

    @RequestMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question) throws SQLException {
        log.info("QuestionController.createQuestionV2");

        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        question.setCreatedDate(date);

        questionDao.insert(question);
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    //    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam Long questionId,
                                           HttpServletRequest request, Model model) throws SQLException {
        log.info("QuestionController.showUpdateQuestionFormV1");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/user/loginForm";
        }

        Question question = questionDao.findByQuestionId(questionId);
        User userFromSession = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(Objects.requireNonNull(userFromSession))) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam Long questionId,
                                           @SessionAttribute(name = "user", required = false) User userFromSession,
                                           Model model) throws SQLException {
        log.info("QuestionController.showUpdateQuestionFormV2");

        if (userFromSession == null) {
            return "redirect:/user/loginForm";
        }

        Question question = questionDao.findByQuestionId(questionId);
        if (!question.isSameUser(Objects.requireNonNull(userFromSession))) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);

        return "/qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/update")
    public String updateQuestion(@RequestParam Long questionId, @RequestParam String title, @RequestParam String contents,
                                 @SessionAttribute(name = "user", required = false) User userFromSession) throws SQLException {
        log.info("QuestionController.updateQuestion");

        if (userFromSession == null) {
            return "redirect:/user/loginForm";
        }

        Question question = questionDao.findByQuestionId(questionId);
        if (!question.isSameUser(userFromSession)) {
            throw new IllegalArgumentException();
        }
        question.updateTitleAndContents(title, contents);
        questionDao.update(question);

        return "redirect:/";
    }

}
