package kuit.springbasic.controller.user;

import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateUserController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User presentUser) {

        memoryUserRepository.findByUserId(presentUser.getUserId()).update(presentUser);
        return "redirect:/user/list";
    }

}
