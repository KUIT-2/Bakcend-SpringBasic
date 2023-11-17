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
//    @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    @GetMapping("/loginForm")
    public String showLoginForm() {
        log.info("LoginController.showLoginForm");
        return "/user/login";
    }


    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/loginFailed")
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

//    // LoginV1
//    @RequestMapping("/login")
//    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest request) {
//        User loggedInUser = new User(userId, password);
//        User user = memoryUserRepository.findByUserId(userId);
//
//        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            return "redirect:/";
//        }
//
//        return "redirect:/user/loginFailed";
//
//    }

//    //LoginV3
//    @RequestMapping("/login")
//    public String login(String userId, String password, HttpServletRequest request) {
//        User loggedInUser = new User(userId, password);
//        User user = memoryUserRepository.findByUserId(userId);
//
//        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            return "redirect:/";
//        }
//
//        return "redirect:/user/loginFailed";
//
//    }

    //LoginV4
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @PostMapping("/login")
    public String login(@ModelAttribute User loggedInUser, HttpServletRequest request) {
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }

        return "redirect:/user/loginFailed";

    }


    /**
     * TODO: logout
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }

}
