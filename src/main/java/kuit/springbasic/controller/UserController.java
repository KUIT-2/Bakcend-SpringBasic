package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/form")
    public String showUserForm(){
        log.info("UserController.showUserForm");
        return "/user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //@PostMapping("/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
        User signUpUser = new User(userId, password, name, email);
        memoryUserRepository.insert(signUpUser);
        log.info("UserController.createUserV1 Success");
        return "redirect:/user/list";
    }
    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User signUpUser) {
        // 회원가입을 위해 User 객체 insert
        memoryUserRepository.insert(signUpUser);
        log.info("UserController.createUserV2 Success");
        return "redirect:/user/list";
    }


    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            // 로그인이 되어있는 유저라면 userList 보여줌
            model.addAttribute("users", memoryUserRepository.findAll());
            log.info("UserController.showUserList loginedUser show userList");
            return "/user/list";
        }
        // 로그인이 안 되어있다면 회원가입 유도
        log.info("UserController.showUserList unLoginedUser show userForm");
        return "/user/form";
    }
    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, Model model) throws SQLException {

        // 개인정보 수정 form

        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            log.info("UserController.showUserUpdateForm loginedUser show user update form");
            return "/user/updateForm";
        }
        log.info("UserController.showUserUpdateForm unLoginedUser");
        return "/home";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //    @PostMapping("/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {

        User user = new User(userId, password, name, email);
        memoryUserRepository.update(user);
        log.info("UserController.updateUserV1 Success");
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User user) {
        // 개인정보 수정
        memoryUserRepository.update(user);
        log.info("UserController.updateUserV2 Success");
        return "redirect:/user/list";
    }

}
