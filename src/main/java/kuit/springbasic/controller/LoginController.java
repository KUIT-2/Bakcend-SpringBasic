package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @GetMapping("/loginFailed")
    public String showLoginFailed() {
        return "/user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User loginUser, HttpServletRequest request) {
        User findUser = memoryUserRepository.findByUserId(loginUser.getUserId());
        if (findUser != null && loginUser.isSameUser(findUser)) {
            request.getSession().setAttribute("user", findUser);
            return "redirect:/";
        }
        return "/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @GetMapping("/logout")  //get..?
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
