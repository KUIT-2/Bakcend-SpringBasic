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

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showForm() {
        log.info("UserController.showForm");
        return "/user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/signup")
    public String createUser(@ModelAttribute User newUser) {
        log.info("UserController.createUser");
        memoryUserRepository.insert(newUser);
        return "redirect:/user/loginForm";
    }


    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request) {
        log.info("UserController.showUserList");
        if (UserSessionUtils.isLoggedIn(request.getSession())) {
            request.setAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }

        return "redirect:/user/loginForm";
    }


    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, HttpServletRequest request) {
        log.info("UserController.showUserUpdateForm");
        if(memoryUserRepository.findByUserId(userId).isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {

            request.setAttribute("user", memoryUserRepository.findByUserId(userId));
            return "/user/updateForm";
        }
        return "redirect:/user/list";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User user) {

        if (user != null) {
            memoryUserRepository.update(user);
            return "redirect:/user/list";
        }

        return "redirect:/user/list";

    }

}
