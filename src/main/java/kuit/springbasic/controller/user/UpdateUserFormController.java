package kuit.springbasic.controller.user;

import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateUserFormController {

    private final MemoryUserRepository memoryUserRepository;
    @RequestMapping("/user/updateForm")
    public ModelAndView updateUserForm(@ModelAttribute User loginUser) {

        User user = memoryUserRepository.findByUserId(loginUser.getUserId());
        if (user != null) {
            return new ModelAndView("/user/updateForm").addObject("user", user);
        }
        return new ModelAndView("redirect:/");
    }
}
