package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@Slf4j
@RequestMapping("/user")            // 코드의 중복 줄여줌
@RequiredArgsConstructor
@Controller
public class LoginController {
    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    // @RequestMapping("/loginForm", method = RequestMethod.GET) == @GetMapping("/loginForm") == @RequestMapping("/loginForm")
    public String showLoginForm(){
        log.info("LoginController.showLoginForm");
        return "user/login";
    }


    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

    // 아래는 loginV4 방식
    @PostMapping("/login")
    // @RequestMapping("/loginForm", method = RequestMethod.POST) == @POSTMapping("/login") == @RequestMapping("/login")
    public String login(@ModelAttribute User loggedInUser, HttpServletRequest request){
        // 세션값을 가져오려면 Servlet이 필요하므로 인자로 HttpServletRequest 추가
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";                // 로그인 실패시
    }


    /**
     * TODO: showLoginFailed -> 위에 로그인 기능에 포함
     */


    /**
     * TODO: logout
     */
    // logIn과 마찬가지로 HttpRequest로부터 세션값 가져온 후, 세션에서 user 지우기 -> ??
    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }
}
