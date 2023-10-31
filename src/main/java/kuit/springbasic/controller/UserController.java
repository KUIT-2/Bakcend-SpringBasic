package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    public String signupForm() {
        log.info("User.signupForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

    @RequestMapping("/signup")
    public String createUserV1(@RequestParam String userId, @RequestParam String password,
            @RequestParam String name, @RequestParam String email) {
        log.info("User.createUserV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    public String createUserV2(@ModelAttribute("user") User user) {
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public ModelAndView userList(HttpServletRequest request) {
        log.info("User.userList");
        if (UserSessionUtils.isLoggedIn(request.getSession())) {
            return new ModelAndView("/user/list").addObject("users",
                    memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public ModelAndView updateUserForm(@ModelAttribute User loginUser) {
        log.info("User.updateUserForm");
        User user = memoryUserRepository.findByUserId(loginUser.getUserId());
        if (user != null) {
            return new ModelAndView("/user/updateForm").addObject("user", user);
        }
        return new ModelAndView("redirect:/");
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    public String updateUserV1(@RequestParam String userId, @RequestParam String password,
            @RequestParam String name, @RequestParam String email) {
        User updateUser = new User(userId, password, name, email);
        memoryUserRepository.findByUserId(userId).update(updateUser);
        return "redirect:/user/list";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User presentUser) {
        log.info("User.updateUserV2");
        memoryUserRepository.findByUserId(presentUser.getUserId()).update(presentUser);
        return "redirect:/user/list";
    }
}
