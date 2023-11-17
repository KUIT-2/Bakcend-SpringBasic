package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Frequency;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @PostMapping("/signup")
    public String createUser(@ModelAttribute User inputUser, HttpServletRequest request) {
        log.info("UserController.createUser");

        User user = new User(
                inputUser.getUserId(),
                inputUser.getPassword(),
                inputUser.getName(),
                inputUser.getEmail());

        memoryUserRepository.insert(user);
        log.info(user.toString());

        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */

    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model) {

        log.info("UserController.showUserList");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)){
            model.addAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";

    }


    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, Model model){
        log.info("UserController.showUserUpdateForm");

        User user = memoryUserRepository.findByUserId(userId);
        if (user != null){
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

    @RequestMapping("/update")
    public String updateUser(@ModelAttribute User user, HttpServletRequest request){
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }


}
