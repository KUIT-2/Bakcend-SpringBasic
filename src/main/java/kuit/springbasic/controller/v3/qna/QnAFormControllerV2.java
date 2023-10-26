package kuit.springbasic.controller.v3.qna;

import kuit.springbasic.core.mvc.v2.ControllerV2;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class QnAFormControllerV2 implements ControllerV2 {

    private boolean isLoggedIn;

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public String execute(Map<String, String> params, Map<String, Object> model) {
        log.info("QnAFormControllerV2");

        if (isLoggedIn) {
            return "/v3/v2/qna/form";
        }
        return "redirect:/v3/v2/user/loginForm";
    }

}
