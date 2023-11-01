package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryQuestionRepository;
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
    public String showLoginForm(Model model) {
        log.info("LoginController.showLoginForm");

        return "user/login";
    }

    /**
     * TODO: showLoginFailed
     *
     */

    @RequestMapping("/user/showLoginFailed")
    public String showLoginFailed(Model model) {
        log.info("LoginController.showLoginFailed");

        return "user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

    @RequestMapping("/user/login")
    public String login(HttpSession session, @RequestParam String userId, @RequestParam String password) {
        log.info("LoginController.login");
        log.info(userId);
        User user = memoryUserRepository.findByUserId(userId);
        if(user.isSameUser(userId, password)){
            log.info("로그인 성공!");
            session.setAttribute("user", user);
            session.setAttribute("userId", userId);
            return "redirect:/";
        }
        return "redirect:/user/showLoginFailed";
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
