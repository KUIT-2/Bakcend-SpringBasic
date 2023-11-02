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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@Slf4j
@RequestMapping("/user")            // 코드의 중복 줄여줌
@RequiredArgsConstructor
@Controller
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm -> ForwardController ??
     */
    @RequestMapping("/form")
    public String signupForm(){
        return "/user/form";
    }

    @RequestMapping("/loginForm")
    public String loginForm(){
        return "/user/loginForm";
    }

    @RequestMapping("/loginFailed")
    public String loginFailed(){
        return "/user/loginFailed";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signup")      // -> id, passwd, name, email 4가지 정보 필요
    public String CreateUser(@RequestParam String Id, @RequestParam String passwd, @RequestParam String name, @RequestParam String email){
        User user = new User(Id, passwd, name, email);
        memoryUserRepository.insert(user);          // user 등록
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList -> UserListController
     */
    @RequestMapping("/list")
    public ModelAndView userList(HttpServletRequest request){
        if(UserSessionUtils.isLoggedIn(request.getSession())){
            return new ModelAndView("/user/list").addObject("user", memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: showUserUpdateForm -> UpdateUserFormController ??
     */
    @RequestMapping("/updateForm")
    public ModelAndView updateUserForm(@ModelAttribute User loginedUser){
        User user = memoryUserRepository.findByUserId(loginedUser.getUserId());
        if(user != null){
            return new ModelAndView("/user/updateForm").addObject("user", user);
        }
        return new ModelAndView("redirect:/");
    }


    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    // updateUserV2 방식 : ModelAttribute 어노테이션을 통해서 User 모델을 인자로 넘김
    @RequestMapping("/update")
    public String updateUser(@ModelAttribute User targetUser){
        memoryUserRepository.findByUserId(targetUser.getUserId()).update(targetUser);       // update
        return "redirect:/user/list";
    }

}
