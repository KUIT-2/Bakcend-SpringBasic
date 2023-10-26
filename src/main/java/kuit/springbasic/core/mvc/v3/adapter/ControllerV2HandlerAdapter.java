package kuit.springbasic.core.mvc.v3.adapter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.core.model.ModelAndView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV2HandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV2);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SQLException {
        ControllerV2 controller = (ControllerV2) handler;
        setControllerFields(request, controller);

        Map<String, String> params = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.execute(params, model);

        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.setModel(model);
        return modelAndView;
    }

}
