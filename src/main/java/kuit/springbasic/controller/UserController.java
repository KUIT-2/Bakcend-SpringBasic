package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String showUserForm(){
        log.info("UserController.showUserForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //@PostMapping("/signup")
    public String createUserV1(@RequestParam String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email){
        log.info("UserController.createUserV1");
        User user = new User(userId, password, name, email);
        this.memoryUserRepository.insert(user);
        return "redirect:/user/loginForm";
    }

    @PostMapping("/signup")
    public String createV2(@ModelAttribute User user){
        log.info("UserController.createUserV2");
        this.memoryUserRepository.insert(user);
        return "redirect:/user/loginForm";
    }


    /**
     * TODO: showUserList
     */
    @GetMapping("list")
    public String showUserList(HttpServletRequest req, Model model){
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
    @GetMapping("/updateForm")
    public String showUserUpdateForm(){
        log.info("UserController.showUpdateForm");
        return "user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //@PostMapping("update")
    public String updateUserV1(@RequestParam String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email){
        this.memoryUserRepository.update(new User (userId, password, name, email));
        return "redirect:/user/list";
    }

    @PostMapping("update")
    public String updateUserV2(@ModelAttribute User user){
        this.memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

}
