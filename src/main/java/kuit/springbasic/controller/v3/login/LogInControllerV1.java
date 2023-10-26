package kuit.springbasic.controller.v3.login;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;


@Slf4j
public class LogInControllerV1 implements ControllerV1 {

    private HttpSession session;

    private final MemoryUserRepository memoryUserRepository = MemoryUserRepository.getInstance();

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Override
    public ModelAndView execute(Map<String, String> params) throws SQLException {
        log.info("LogInControllerV1");

        String userId = params.get("userId");
        String password = params.get("password");

        User loggedInUser = new User(userId, password);
        User user = memoryUserRepository.findByUserId(userId);

        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
            session.setAttribute("user", user);
            return new ModelAndView("redirect:/v3/v1");
        }
        return new ModelAndView("redirect:/v3/v1/user/loginFailed");
    }

}
