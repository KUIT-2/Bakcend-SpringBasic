package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final MemoryQuestionRepository questionRepository;

    @GetMapping("/form")
    public String showQuestionForm(HttpSession session) {
        if(UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    @PostMapping("/create")
    public String createQuestion(@ModelAttribute Question question) {
        questionRepository.insert(question);
        return "redirect:/";
    }

    @GetMapping("/updateForm")
    public String showUpdateQuestionForm(HttpSession session, @RequestParam int questionId, Model model) {
        if(!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findByQuestionId(questionId);
        if(!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            return "/qna/show?questionId=" + questionId;
        }
        model.addAttribute("question", question);
        return "/qna/updateForm.jsp";
    }

    @PostMapping("/update")
    public String updateQuestion(HttpSession session, @RequestParam int questionId, @RequestParam String title, @RequestParam String contents) {
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findByQuestionId(questionId);

        if (!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            throw new IllegalArgumentException();
        }

        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);

        return "redirect:/";
    }
}
