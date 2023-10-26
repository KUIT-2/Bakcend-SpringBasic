package kuit.springbasic.controller.v3.user;

import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class UpdateUserFormControllerV2 implements ControllerV2 {

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) throws SQLException {
        log.info("UpdateUserFormControllerV2");

        String userId = params.get("userId");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            model.put("user", user);
            return "/v3/v2/user/updateForm";
        }
        return "redirect:/v3/v2";
    }

}
