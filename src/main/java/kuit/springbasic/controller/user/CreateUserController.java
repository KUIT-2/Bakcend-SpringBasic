package kuit.springbasic.controller.user;

import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CreateUserController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/user/signup")
    public String createUser(@RequestParam String userId, @RequestParam String password,
            @RequestParam String name, @RequestParam String email) {
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }
}
