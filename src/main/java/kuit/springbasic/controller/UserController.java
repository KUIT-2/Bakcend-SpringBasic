package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/form")
    public String showUserForm() {
        log.info("showUserForm");
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @PostMapping("/signup")
    public ModelAndView createUserV1(@RequestParam String userId,
                                     @RequestParam String password,
                                     @RequestParam String name,
                                     @RequestParam String email,
                                     HttpServletRequest request) {
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        ModelAndView modelAndView = new ModelAndView("redirect:/user/list");

        return modelAndView;
    }

//    @PostMapping("/signup")
//    public ModelAndView createUserV2(@ModelAttribute User createUser) {
//        memoryUserRepository.insert(createUser);
//
//        ModelAndView modelAndView = new ModelAndView("redirect:/user/list");
//
//        return modelAndView;
//    }


    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public ModelAndView showUserList() {
        log.info("showUserList");
        // 로그인 되어있는 경우만 보여주는 로직 추가해야함

        ModelAndView modelAndView = new ModelAndView("user/list");

        Collection<User> users = memoryUserRepository.findAll();
        modelAndView.addObject("users", users);

        return modelAndView;
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public ModelAndView showUserUpdateForm(HttpServletRequest request) {
        log.info("showUserUpdateForm");

        String userId = request.getParameter("userId");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
//            request.setAttribute("user",user);
            ModelAndView modelAndView = new ModelAndView("/user/updateForm");
            modelAndView.addObject("user", user);

            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        return modelAndView;
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @PostMapping("/update")
//    public ModelAndView updateUserV1(@RequestParam String userId,
//                                   @RequestParam String password,
//                                   @RequestParam String name,
//                                   @RequestParam String email) {
//        log.info("updateUser");
//
//        memoryUserRepository.update(new User(userId, password, name, email));
//
//        ModelAndView modelAndView = new ModelAndView("redirect:/user/list");
//
//        return modelAndView;
//    }

    @PostMapping("/update")
    public ModelAndView updateUserV2(@ModelAttribute User updateUser) {
        log.info("updateUser");

        memoryUserRepository.update(updateUser);

        ModelAndView modelAndView = new ModelAndView("redirect:/user/list");

        return modelAndView;
    }

}
