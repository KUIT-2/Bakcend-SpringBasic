package kuit.springbasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForwardController {

    //밑의 두 방식에 차이가 있는지..? model이 없다면 굳이 mav를 써야하는지?

//    @RequestMapping("/user/loginFailed")
//    public String loginFailedForm() {
//        return "/user/loginFailed";
//    }

    @GetMapping("/user/loginFailed")
    public ModelAndView loginFailedForm() {
        return new ModelAndView("/user/loginFailed");
    }
}
