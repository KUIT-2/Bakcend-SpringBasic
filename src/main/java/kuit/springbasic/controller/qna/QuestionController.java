package kuit.springbasic.controller.qna;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import kuit.springbasic.db.MemoryQuestionRepository;
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
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final MemoryQuestionRepository memoryQuestionRepository;

    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/qna/form")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("QuestionController.showQuestionForm");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/user/login";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */

    public ModelAndView createQuestionV1(@RequestParam String writer, @RequestParam String title,
            @RequestParam String contents) {
        Question question = new Question(MemoryQuestionRepository.getPK(), writer, title, contents,
                Date.valueOf(LocalDate.now()), 0);

        memoryQuestionRepository.insert(question);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/qna/create")
    public String createQuestionV2(@ModelAttribute Question question1) {
        log.info("QuestionController.createQuestionV2");
        memoryQuestionRepository.insert(question1);
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */

    @RequestMapping("/qna/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam int questionId, HttpServletRequest request,
            Model model) {
        log.info("QuestionController.showUpdateQuestionFormV1");
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/user/login";
        }
        Question question = memoryQuestionRepository.findByQuestionId(questionId);

        if (!question.isSameUser(
                Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            return "/qna/show?questionId=" + questionId;
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    //LoginController에서 로그인 성공시에 session에 setAttribute한 user가 있나 확인하여 사용
    public String showUpdateQuestionFormV2(@RequestParam int questionId,
            @SessionAttribute("user") User user, Model model) {
        if (user == null) {
            return "redirect:/user/login";
        }
        Question question = memoryQuestionRepository.findByQuestionId(questionId);

        if (!question.isSameUser(user)) {
            return "/qna/show?questionId=" + questionId;
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */

    @RequestMapping("/qna/update")
    public String updateQuestion(HttpServletRequest request, @RequestParam int questionId) {
        log.info("QuestionController.updateQuestion");
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/user/login";
        }
        Question question = memoryQuestionRepository.findByQuestionId(questionId);

        if (!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            throw new IllegalArgumentException();
        }
        question.updateTitleAndContents(request.getParameter("title"), request.getParameter("contents"));
        memoryQuestionRepository.update(question);

        return "redirect:/";
    }
}
