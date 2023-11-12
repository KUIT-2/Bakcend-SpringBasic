package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/loginForm")
    public String showLoginForm() {
        log.info("LoginController.showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpServletRequest request) {
        log.info("LoginController.login");
        User loggedInUser = new User(userId, password);
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    //방법 1 -> 추천방식은 아님
//    @RequestMapping("/login")
//    public String login(String userId, String password, HttpServletRequest request) {
//        User loggedInUser = new User(userId, password);
//        User user = memoryUserRepository.findByUserId(userId);
//        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }
    //방법 2
//    @RequestMapping("/login")
//    public String login(@ModelAttribute User loggedInUser, HttpServletRequest request) {
//        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());
//        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }

    /**
     * TODO: showLoginFailed
     */
   @RequestMapping("/loginFailed")
   public String loginFailed(){
       log.info("LoginController.loginFailed");
       return "redirect:/user/loginForm"; //id, passward 잘못입력하면 그냥 다시 로그인페이지로 리다이렉트
   }

    /**
     * TODO: logout
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("LoginController.logout");
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/"; // 로그아웃 후 홈페이지로 리다이렉트
    }
}