package kuit.springbasic.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String showLoginForm(){
    log.info("LoginController.showLoginForm");

    return "user/login";
}
    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/user/loginFailed")
    public String showLoginFailed(){
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
    public String login(HttpSession session, @RequestParam String userId, @RequestParam String password){
        log.info("LoginController.login");
        return "/user/loginFailed";
    }
    @RequestMapping(value = "/login")
    public String login(@ModelAttribute User loginedUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = memoryUserRepository.findByUserId(loginedUser.getUserId());

        if (user != null && user.getUserId().equals(loginedUser.getUserId())
                && user.getPassword().equals(loginedUser.getPassword())) {
            session.setAttribute("user", user);
            log.info("LoginContriller.loginSucess");
            return "redirect:/";
        }

        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/user/logout")
    public String logout(HttpSession session) {
        log.info("LoginController.logout");
        session.removeAttribute("user");

        return "redirect:/";
    }

}
