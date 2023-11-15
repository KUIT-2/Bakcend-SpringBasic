package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");

        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/signup")
    public String createUser(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email) {
        log.info("UserController.createUser");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest req, HttpServletResponse resp, Model model) {
        log.info("UserController.showUserList");
        HttpSession session = req.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, Model model) {
        log.info("UserController.showUserUpdateForm");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/update")
    public String updateUser(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email) {
        log.info("UserController.updateUser");
        User user = new User(userId, password, name, email);
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }
}
