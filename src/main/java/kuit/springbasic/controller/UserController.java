package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.dao.UserDao;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    //private final MemoryUserRepository memoryUserRepository;
    private final UserDao userDao;

    @GetMapping("form")
    public String showUserForm() {
        log.info("UserController.showUserForm");
        return "/user/form";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user) {
        log.info("UserController.signUp");
        userDao.insert(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String showUserList(HttpSession session, Model model) {
        log.info("UserController.showUserList");
        if(UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userDao.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, Model model) {
        log.info("UserController.showUserUpdateForm");
        User user = userDao.findByUserId(userId);
        if(user != null) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        log.info("UserController.updateUser");
        userDao.update(user);
        return "redirect:/user/list";
    }

}
