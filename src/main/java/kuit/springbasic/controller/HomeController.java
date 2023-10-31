package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller //controlloer임을 명시하여 spring container에 등록.
@RequiredArgsConstructor    //final 변수를 가진 친구를 생성자 인자로 포함하여 자동 생성
public class HomeController {

//    @Autowired // spring bean으로 등록된 친구 가져다 쓰기
//    public HomeController(MemoryQuestionRepository memoryQuestionRepository) {
//        this.memoryQuestionRepository = memoryQuestionRepository;
//    }

    private final MemoryQuestionRepository memoryQuestionRepository;

    @RequestMapping("/homeV1")
//    @RequestMapping("/")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        log.info("HomeController.homeV1");

        ModelAndView modelAndView = new ModelAndView("home");

        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @RequestMapping("/homeV2")
//    @RequestMapping("/")
//    인자값을 사용하지 않으면 버려도됨!
    public ModelAndView showHomeV2() {
        log.info("HomeController.homeV2");

        ModelAndView modelAndView = new ModelAndView("home");

        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @RequestMapping("/")
//    모델만 받아서 addAttribute 후 주소 String을 return해도 스프링이 알아서 해줌
    public String showHomeV3(Model model) {
        log.info("HomeController.homeV3");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

}
