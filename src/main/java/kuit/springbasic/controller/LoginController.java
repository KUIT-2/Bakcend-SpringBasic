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
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {
    private final MemoryUserRepository memoryUserRepository;
    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    public String showLoginForm(){
        log.info("LoginController.showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @GetMapping("/loginFailed")
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
    @PostMapping("/login")
    public String login(@ModelAttribute User loggedInUser, HttpServletRequest request) {

        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());
        // ModelAttribute의 User 객체에서 UserID를 뽑아온 후 memoryUserRepository를 통해 저장된 User 객체를 조회

        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
            // User가 존재하고 ID, PW가 맞으면 로그인
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            log.info("LoginController.login Success");
            return "redirect:/";
        }
        //// User가 존재하지 않거나 ID, PW가 틀리면 login Fail로 리다이렉트
        log.info("LoginController.login Failed");
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("LoginController.logout");
        HttpSession session = request.getSession(false);
        // logoutFailed.jsp는 없으므로 예외처리 하지 않았음
        if (session != null) {
            session.invalidate();
            //세션 정보 삭제
        }
        return "redirect:/";
    }
}
