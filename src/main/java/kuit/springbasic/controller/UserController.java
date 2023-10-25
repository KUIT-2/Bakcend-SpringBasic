package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.web.domain.User;
import kuit.springbasic.web.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //    @RequestMapping("/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
        log.info("UserController.createUserV1");

        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("UserController.createUserV2");
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public String showUserList(HttpServletRequest request, Model model) {
        log.info("UserController.showUserList");

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, Model model) throws SQLException {
        log.info("UserController.showUserUpdateForm");

        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "/home";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //    @RequestMapping("/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
        log.info("UserController.updateUserV1");

        User user = new User(userId, password, name, email);
        memoryUserRepository.update(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User user) {
        log.info("UserController.updateUserV2");
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

}
