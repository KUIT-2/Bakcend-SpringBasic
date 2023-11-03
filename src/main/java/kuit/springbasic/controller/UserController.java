package kuit.springbasic.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm(){
        log.info("UserController.showUserForm");
        return "/user/form";
    }
    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email){
        log.info("UserController.createUserV1");
        User user = new User(userId, password,name,email);
        memoryUserRepository.insert(user);
        return "/user/list";
    }

    //@RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user){
        log.info("UserController.createUserV2");
        memoryUserRepository.insert(user);
        return "/user/list";
    }


    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public String showUserList(HttpServletRequest request) {
        log.info("UserController.showUserList");
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            modelAndView.addObject("users", memoryUserRepository.findAll());
            return "user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(HttpServletRequest request){
        log.info("UserController.showUserUpdateForm");
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            modelAndView.addObject("users", memoryUserRepository.findAll());
            return "user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //@RequestMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email){
        log.info("UserController.updateUserV1");
        User user = new User(userId, password,name,email);
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User user){
        log.info("UserController.createUserV2");
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }
}
