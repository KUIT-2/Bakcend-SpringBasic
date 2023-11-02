package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */

    @RequestMapping("/user/loginForm")
    public String showLoginForm() {
        log.info("LoginController.showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/user/loginFailed")
    public String loginFailed() {
        log.info("LoginController.loginFailed");
        return "/user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping(value = "/user/login")
    public String login(@ModelAttribute User loginedUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = memoryUserRepository.findByUserId(loginedUser.getUserId());

        if (user != null && user.isSameUser(loginedUser.getUserId(), loginedUser.getPassword())) {
            session.setAttribute("user", user);
            return "redirect:/";
        }

        return "redirect:/user/loginFailed";
    }


    /**
     * TODO: logout
     */

}
