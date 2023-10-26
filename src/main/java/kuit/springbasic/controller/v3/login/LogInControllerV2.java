package kuit.springbasic.controller.v3.login;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;


@Slf4j
public class LogInControllerV2 implements ControllerV2 {

    private HttpSession session;

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) throws SQLException {
        log.info("LogInControllerV2");

        String userId = params.get("userId");
        String password = params.get("password");

        User loggedInUser = new User(userId, password);
        User user = memoryUserRepository.findByUserId(userId);

        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
            session.setAttribute("user", user);
            return "redirect:/v3/v2";
        }
        return "redirect:/v3/v2/user/loginFailed";
    }

}
