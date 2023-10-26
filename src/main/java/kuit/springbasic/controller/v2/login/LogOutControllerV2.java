package kuit.springbasic.controller.v2.login;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.core.mvc.v2.ControllerV2;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class LogOutControllerV2 implements ControllerV2 {

    private HttpSession session;

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) {
        log.info("LogOutControllerV2");
        session.removeAttribute("user");
        return "redirect:/v2";
    }

}
