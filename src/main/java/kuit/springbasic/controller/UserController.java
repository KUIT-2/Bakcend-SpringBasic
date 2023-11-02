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
            return "/user/list";
        }

        return "redirect:/user/loginForm";
    }


    /**
     * TODO: showUserUpdateForm
     */

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

}
