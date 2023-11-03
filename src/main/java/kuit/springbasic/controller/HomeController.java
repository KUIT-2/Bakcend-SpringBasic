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
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemoryQuestionRepository memoryQuestionRepository;

//    @Autowired
//    public HomeController(MemoryQuestionRepository memoryQuestionRepository) {
//        this.memoryQuestionRepository = memoryQuestionRepository;
//    }

    //localhost:8080/ 으로 넘어오는 내용에 대해서는 이 컨트롤러를 통해서 이 내용으로 맵핑함
//    @RequestMapping("/")
//    public ModelAndView showHome(){
//        log.info("HomeController.showHome");
//
//        List<Question> questions = memoryQuestionRepository.findAll();
//        ModelAndView mav = new ModelAndView("home");
//        //mav.getModel().put("questions", questions);
//        mav.addObject("questions",questions);
//        return mav;
//    }

    //modelandview 말고 string으로 반환하는 방법
    @RequestMapping("/")
    public String showHome2(Model model){
        log.info("HomeController.showHome2");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }


}
