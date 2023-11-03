package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public String showUserForm() {
        return "/user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @PostMapping("/signup")
//    public String createUserV1(@RequestParam String userId,
//                               @RequestParam String password,
//                               @RequestParam String name,
//                               @RequestParam String email) {
//        User user = new User(userId, password, name, email);
//        memoryUserRepository.insert(user);
//        return "redirect:/";
//    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        memoryUserRepository.insert(user);
        return "redirect:/";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public String showUserList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)){
            Collection<User> users = memoryUserRepository.findAll();
            request.setAttribute("users", users);
            return "/user/list";
        }
        return "/user/login";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(HttpServletRequest request, @RequestParam String userId) {
        User loginUser = (User) request.getSession().getAttribute("user");
        User findUser = memoryUserRepository.findByUserId(userId);
        if (loginUser.isSameUser(findUser)) {
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
    public String updateUser(@ModelAttribute User user) {
        User findUser = memoryUserRepository.findByUserId(user.getUserId());
        findUser.update(user);
        return "redirect:/user/list";
    }

}
