package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    public String showLoginForm() {
        log.info("showLoginForm");
        return "user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @GetMapping("/loginFailed")
    public String showLoginFailed() {
        log.info("showLoginFailed");
        return "user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
//    @PostMapping("/login")
//    public ModelAndView loginV2(@RequestParam String userId,
//                                @RequestParam String password,
//                                HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        User logInUser = new User(userId, password);
//        User user = memoryUserRepository.findByUserId(userId);
//
//        if (user != null && user.isSameUser(logInUser)) {
//            session.setAttribute("user", user);
//            ModelAndView modelAndView = new ModelAndView("redirect:/");
//
//            return modelAndView;
//        }
//        ModelAndView modelAndView = new ModelAndView("redirect:/user/loginFailed");
//
//        return modelAndView;
//    }

    @PostMapping("/login")
    public ModelAndView loginV4(@ModelAttribute User user,
                                HttpServletRequest request) {
        HttpSession session = request.getSession();
        User logInUser = memoryUserRepository.findByUserId(user.getUserId());

        if (user != null && user.isSameUser(logInUser)) {
            session.setAttribute("user", user);
            ModelAndView modelAndView = new ModelAndView("redirect:/");

            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/user/loginFailed");

        return modelAndView;
    }

    /**
     * TODO: logout
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        return modelAndView;
    }

}
