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
    public String showLoginForm(){
        log.info("LoginController.showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    //loginV1
/*        @RequestMapping("/login")
    public String login(@RequestParam("userID") String userId, @RequestParam("password") String password, HttpServletRequest request){
        User loggedInUser = new User(userId,password);
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }*/

    //loginV2
/*    @RequestMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpServletRequest request){
        User loggedInUser = new User(userId,password);
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }*/

    //loginV3
/*    @RequestMapping("/login")
    public String login(String userId, String password, HttpServletRequest request){
        User loggedInUser = new User(userId,password);
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }*/

    //loginV4
    @RequestMapping("/login")
    public String login(@ModelAttribute User loggedInUser, HttpServletRequest request){
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }
    /**
     * TODO: logout
     */
    @RequestMapping("/logout")
    public String logOut(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }

}