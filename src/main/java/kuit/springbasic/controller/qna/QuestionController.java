package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final MemoryQuestionRepository memoryQuestionRepository;

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {
        if (UserSessionUtils.isLoggedIn(request.getSession())) {
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }


    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @PostMapping("/create")
    public String createQuestion(@ModelAttribute Question question) {
        memoryQuestionRepository.insert(question);
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
//    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam int questionId, HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/user/loginForm";
        }
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        if (question.isSameUser(user)) {
            model.addAttribute("question",question);
            return "/qna/updateForm";
        }
        return "redirect:/";
    }

    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam int questionId,
                                         @SessionAttribute(value = "user", required = false) User user, Model model) {
        if (user == null) {
            return "redirect:/user/loginForm";
        }
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        if (question.isSameUser(user)) {
            model.addAttribute("question",question);
            return "/qna/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateQuestion
     */
    @PostMapping("/update")
    public String updateQuestion(@RequestParam int questionId, @RequestParam String title, @RequestParam String contents) {
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        question.updateTitleAndContents(title, contents);
        memoryQuestionRepository.update(question);
        return "redirect:/";
    }
}
