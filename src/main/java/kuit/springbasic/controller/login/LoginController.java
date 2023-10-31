package kuit.springbasic.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")    //공통 url 통합 가능
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    public String showLoginForm() {
        log.info("LoginController.showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */

//     TODO: login
//     loginV1 : @RequestParam("")
//    @PostMapping("/login")
//    public String loginV1(@RequestParam("userId") String userId,
//            @RequestParam("password") String password, HttpServletRequest request) {
//
//        User loggedInUser = new User(userId, password);
//        User user = memoryUserRepository.findByUserId(userId);
//
//        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword()
//                .equals(loggedInUser.getPassword())) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }
//
//    //     loginV2 : @RequestParam : param명과 변수명이 같을 경우 "" 생략해도 됨
//    @PostMapping("/login")
//    public String loginV2(@RequestParam String userId, @RequestParam String password,
//            HttpServletRequest request) {
//
//        User loggedInUser = new User(userId, password);
//        User user = memoryUserRepository.findByUserId(userId);
//
//        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword()
//                .equals(loggedInUser.getPassword())) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }
//
//    //     loginV3 : @RequestParam 생략(비추천) : 위에 이어서 어노테이션마저 생략해도 됨.
////    협업과정에서의 소통에서 원활한 소통에 방해가 될 수 있기에 어노테이션은 붙여주자..
//    @PostMapping("/login")
//    public String loginV3(String userId, String password, HttpServletRequest request) {
//
//        User loggedInUser = new User(userId, password);
//        User user = memoryUserRepository.findByUserId(userId);
//
//        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword()
//                .equals(loggedInUser.getPassword())) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }

    //     loginV4 : @ModelAttribute : UserId와 password값을 각 String으로 받지 않고 User 객체로 받아버리기
    @PostMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser, HttpServletRequest request) {

        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword()
                .equals(loggedInUser.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

//    TODO: logout


}
