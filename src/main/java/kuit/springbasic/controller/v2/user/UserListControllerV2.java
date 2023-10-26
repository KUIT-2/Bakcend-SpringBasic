package kuit.springbasic.controller.v2.user;

import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.db.MemoryUserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class UserListControllerV2 implements ControllerV2 {

    private boolean isLoggedIn = false;

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) {
        log.info("ListUserControllerV2");

        if (isLoggedIn) {
            model.put("users", memoryUserRepository.findAll());
            return "/v2/user/list";
        }
        return "redirect:/v2/user/loginForm";
    }

}
