package kuit.springbasic.controller.v1.user;

import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class UpdateUserFormControllerV1 implements ControllerV1 {

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();

    @Override
    public ModelAndView execute(Map<String, String> params) throws SQLException {
        log.info("UpdateUserFormControllerV1");

        String userId = params.get("userId");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/v1/user/updateForm");
            modelAndView.getModel().put("user", user);
            return modelAndView;
        }
        return new ModelAndView("redirect:/v1");
    }

}
