package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
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
    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user){
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model){
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
    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId")String userId, Model model){
        log.info("UserController.showUserUpdateForm");
        User user = memoryUserRepository.findByUserId(userId);
        if(user!=null){
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
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user){
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

}
