package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.dao.UserDao;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    //private final MemoryUserRepository memoryUserRepository;
    private final UserDao userDao;

    @GetMapping("/loginForm")
    public String showLoginForm() {
        log.info("LoginController.showLoginForm");
        return "/user/login";
    }

    @GetMapping("/loginFailed")
    public String showLoginFailed() {
        log.info("LoginController.showLoginFailed");
        return "/user/loginFailed";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User loggedInUser, HttpServletRequest request) {
        log.info("LoginController.login");
        User user = userDao.findByUserId(loggedInUser.getUserId());

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        log.info("LoginController.logOut");
        session.removeAttribute("user");
        return "redirect:/";
    }

}
