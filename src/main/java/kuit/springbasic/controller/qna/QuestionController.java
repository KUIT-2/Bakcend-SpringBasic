package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    @Autowired
    private MemoryQuestionRepository memoryQuestionRepository;

    @Autowired
    private MemoryAnswerRepository memoryAnswerRepository;

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showQuestionForm() {
        log.info("showQuestionForm");
        return "qna/form";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
//    @PostMapping("/create")
//    public ModelAndView createQuestionV1(@RequestParam String writer,
//                                     @RequestParam String title,
//                                     @RequestParam String contents) {
//        Question question = new Question(writer, title, contents, 0);
//        memoryQuestionRepository.insert(question);
//        ModelAndView modelAndView = new ModelAndView("redirect:/");
//
//        return modelAndView;
//    }

    @PostMapping("/create")
    public ModelAndView createQuestionV2(@ModelAttribute Question question) {
        memoryQuestionRepository.insert(question);
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        return modelAndView;
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */

    @GetMapping("/updateForm")
    public ModelAndView showUpdateQuestionForm(@RequestParam Integer questionId,
                                               HttpServletRequest request,
                                               Model model) {
        log.info("showUpdateQuestionForm");

        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);


        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            ModelAndView modelAndView = new ModelAndView("redirect:/users/loginForm");
            return modelAndView;
        }


        if (!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            ModelAndView modelAndView = new ModelAndView("/qna/show?questionId=" + questionId);
            return modelAndView;
        }

        request.setAttribute("question", question);
        request.setAttribute("answers",answers);

        ModelAndView modelAndView = new ModelAndView("qna/updateForm");
        return modelAndView;
    }

//    여기부터 다시 @SessionAttribute 다시
//    @GetMapping("/updateForm")
//    public ModelAndView showUpdateQuestionForm(@RequestParam Integer questionId,
//                                               @SessionAttribute String session,
//                                               Model model) {
//        log.info("showUpdateQuestionForm");
//
//        Question question = memoryQuestionRepository.findByQuestionId(questionId);
//        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);
//        session("question", question);
//        request.setAttribute("answers",answers);
//        ModelAndView modelAndView = new ModelAndView("qna/updateForm");
//
//        return modelAndView;
//    }



    /**
     * TODO: updateQuestion
     */
    @PostMapping("/update")
    public ModelAndView updateQuestion(@RequestParam Integer questionId, HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            ModelAndView modelAndView = new ModelAndView("redirect:/users/loginForm");
            return modelAndView;
        }

        Question question = memoryQuestionRepository.findByQuestionId(questionId);

        if (!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))) {
            throw new IllegalArgumentException();
        }

        question.updateTitleAndContents(request.getParameter("title"),request.getParameter("contents"));
        memoryQuestionRepository.update(question);


        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;

    }


}
