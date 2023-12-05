package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
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
    //createUserV1
/*    @RequestMapping("/signup")
    public ModelAndView createUser(@RequestParam("userId")String userId,@RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email){
        log.info("UserController.createUser");
        User signUpUser = new User(userId,password,name,email);
        memoryUserRepository.insert(signUpUser);
        return new ModelAndView("redirect:/user/list");
    }*/

    //createUserV2
    @RequestMapping("/signup")
    public ModelAndView createUser(@ModelAttribute User signUpUser){
        log.info("UserController.createUser");
        memoryUserRepository.insert(signUpUser);
        return new ModelAndView("redirect:/user/list");
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public ModelAndView showUserList(HttpServletRequest httpServletRequest){
        log.info("UserContorller.showUserList");
        HttpSession httpSession = httpServletRequest.getSession();
        if(UserSessionUtils.isLoggedIn(httpSession)){
            return new ModelAndView("/user/list").addObject("users",memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public ModelAndView showUserUpdateForm(@RequestParam("userId")String userId){
        log.info("UserController.showUserUpdateForm");
        User user = memoryUserRepository.findByUserId(userId);
        if(user != null){
            return new ModelAndView("/user/updateForm").addObject("user",user);
        }
        return new ModelAndView("redirect:/");
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //updateUserV1
/*    @RequestMapping("/update")
    public ModelAndView updateUser(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email){
        log.info("UserController.updateUser");
        User updatedUser = new User(userId,password,name,email);
        memoryUserRepository.update(updatedUser);
        return new ModelAndView("redircet:/user/list");
    }*/
    //updateUserV2
    @RequestMapping("/update")
    public ModelAndView updateUser(@ModelAttribute User updatedUser){
        log.info("UserController.updateUser");
        memoryUserRepository.update(updatedUser);
        return new ModelAndView("redircet:/user/list");
    }

}