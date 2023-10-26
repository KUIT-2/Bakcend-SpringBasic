package kuit.springbasic.controller.v1.user;

import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class UpdateUserControllerV1 implements ControllerV1 {

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();


    @Override
    public ModelAndView execute(Map<String, String> params) throws SQLException {
        log.info("UpdateUserControllerV1");

        User user = new User(params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
        memoryUserRepository.update(user);

        return new ModelAndView("redirect:/v1/user/list");
    }

}
