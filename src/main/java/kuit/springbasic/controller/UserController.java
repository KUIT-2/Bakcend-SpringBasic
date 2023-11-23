package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("/form")
    public String signupForm() {
        log.info("User.signupForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

    @PostMapping("/signup")
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
    @GetMapping("/list")
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
    @GetMapping("/updateForm")
    public ModelAndView updateUserForm(HttpServletRequest request) {
        log.info("User.updateUserForm");
        HttpSession session = request.getSession();
        String userId = request.getParameter("userId");
        User updateUser = memoryUserRepository.findByUserId(userId);
        if (updateUser != null && session.getAttribute("user").equals(updateUser)) {
            return new ModelAndView("/user/updateForm").addObject("user", updateUser);
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

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User presentUser) {
        log.info("User.updateUserV2");
        memoryUserRepository.findByUserId(presentUser.getUserId()).update(presentUser);
        return "redirect:/user/list";
    }
}
