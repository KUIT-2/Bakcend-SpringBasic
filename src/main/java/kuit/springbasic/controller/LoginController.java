package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;
    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    public String showLoginForm(){
        log.info("LoginController.showLoginForm");
        return "user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @GetMapping("/loginFailed")
    public String showLoginFailed(){
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
    //@PostMapping("/login")
    public String loginV1( @RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest req){
        log.info("LoginController.loginV1");
        User userById = this.memoryUserRepository.findByUserId(userId);
        if(userById == null || !password.equals(userById.getPassword())){
            return "redirect:/user/loginFailed";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userById);
        return "redirect:/";
    }

    //@PostMapping("/login")
    public String loginV2(@RequestParam String userId, @RequestParam String password, HttpServletRequest req){
        log.info("LoginController.loginV2");
        User userById = this.memoryUserRepository.findByUserId(userId);
        if(userById == null || !password.equals(userById.getPassword())){
            return "redirect:/user/loginFailed";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userById);
        return "redirect:/";
    }

    //@PostMapping("/login")
    public String loginV3(String userId, String password, HttpServletRequest req){
        log.info("LoginController.loginV3");
        User userById = this.memoryUserRepository.findByUserId(userId);
        if(userById == null || !password.equals(userById.getPassword())){
            return "redirect:/user/loginFailed";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userById);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(@ModelAttribute User user, HttpServletRequest req){
        log.info("LoginController.loginV4");
        User userById = this.memoryUserRepository.findByUserId(user.getUserId());
        if(userById == null || !user.getPassword().equals(userById.getPassword())){
            return "redirect:/user/loginFailed";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userById);
        return "redirect:/";
    }

    /**
     * TODO: logout
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        log.info("LoginController.logout");
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }

}
