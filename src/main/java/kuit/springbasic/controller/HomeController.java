package kuit.springbasic.controller;

import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemoryQuestionRepository memoryQuestionRepository;

    @RequestMapping("/")
    public String showHomeV3(Model model) {
        log.info("HomeController.homeV3");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

}
