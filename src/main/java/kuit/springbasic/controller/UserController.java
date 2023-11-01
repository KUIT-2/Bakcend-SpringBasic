package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.Collection;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {


    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");

        return "user/form";
    }

    //redirect -> url주소
    // return: jsp파일 경로 아ㅏ마도?

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/signup")
    public String createUser(@RequestParam String userId, @RequestParam String password,
                               @RequestParam String name, @RequestParam String email) {
        log.info("UserController.createUser");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);

        return "redirect:/";
    }


    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request) {
        log.info("UserController.showUserList");
        Collection<User> users = memoryUserRepository.findAll();
        request.setAttribute("users", users);

        return "user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(HttpSession session, HttpServletRequest request) {
        log.info("UserController.showUserUpdateForm");
        request.setAttribute("user",session.getAttribute("user"));


        return "user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/update")
    public String updateUser(HttpSession session,@RequestParam String password,
                                     @RequestParam String name, @RequestParam String email) {
        log.info("UserController.updateUser");
        // userid는 따로 안받아져서 생기는 문제?

        User user = new User((String) session.getAttribute("userId"), password, name, email);
        memoryUserRepository.update(user);

        return "redirect:/";
    }

}
