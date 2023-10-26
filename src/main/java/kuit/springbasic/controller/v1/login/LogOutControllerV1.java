package kuit.springbasic.controller.v1.login;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.core.model.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class LogOutControllerV1 implements ControllerV1 {

    private HttpSession session;

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Override
    public ModelAndView execute(Map<String, String> params) {
        log.info("LogOutControllerV1");
        session.removeAttribute("user");
        return new ModelAndView("redirect:/v1");
    }

}
