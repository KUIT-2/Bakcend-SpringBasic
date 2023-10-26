package kuit.springbasic.controller.v3.user;

import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class CreateUserControllerV1 implements ControllerV1 {

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();

    @Override
    public ModelAndView execute(Map<String, String> params) {
        log.info("CreateUserControllerV1");

        User user = new User(params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
        memoryUserRepository.insert(user);

        return new ModelAndView("redirect:/v3/v1/user/list");
    }

}
