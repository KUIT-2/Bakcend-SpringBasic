package kuit.springbasic.controller.v3.user;

import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.db.MemoryUserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class UserListControllerV1 implements ControllerV1 {

    private boolean isLoggedIn = false;

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public ModelAndView execute(Map<String, String> params) {
        log.info("UserListControllerV1");

        if (isLoggedIn) {
            ModelAndView modelAndView = new ModelAndView("/v3/v1/user/list");
            modelAndView.getModel().put("users", memoryUserRepository.findAll());
            return modelAndView;
        }
        return new ModelAndView("redirect:/v3/v1/user/loginForm");
    }

}
