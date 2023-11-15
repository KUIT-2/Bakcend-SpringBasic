package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showLoginFailed() {
        log.info("LoginController.showLoginFailed");

        return "/user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping("/user/login")
    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest req) {
        log.info("LoginController.login");
        HttpSession session = req.getSession();

        User loginedUser = new User(userId, password);
        User user = memoryUserRepository.findByUserId(userId);
        if(user != null && user.isSameUser(loginedUser)) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest req) {
        log.info("LoginController.logout");

        HttpSession session = req.getSession();
        session.removeAttribute("user");

        return "redirect:/";
    }

}
