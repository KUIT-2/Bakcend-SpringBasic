package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserListController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/user/list")
    public ModelAndView userList(HttpServletRequest request) {
        if (UserSessionUtils.isLoggedIn(request.getSession())) {
            return new ModelAndView("/user/list").addObject("users",
                    memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }
}
