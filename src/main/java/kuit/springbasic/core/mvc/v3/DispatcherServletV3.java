package kuit.springbasic.core.mvc.v3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.core.mvc.v3.adapter.ControllerV1HandlerAdapter;
import kuit.springbasic.core.mvc.v3.adapter.ControllerV2HandlerAdapter;
import kuit.springbasic.core.mvc.v3.adapter.HandlerAdapter;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.core.mvc.view.JsonView;
import kuit.springbasic.core.mvc.view.JspView;
import kuit.springbasic.core.mvc.view.View;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebServlet(name = "dispatcherV3", urlPatterns = "/v3/*", loadOnStartup = 1)
public class DispatcherServletV3 extends HttpServlet {

    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String JSON_VIEW_PREFIX = "jsonView";

    private RequestMappingV3 requestMapping;
    private final List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    @Override
    public void init() {
        requestMapping = new RequestMappingV3();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV1HandlerAdapter());
        handlerAdapters.add(new ControllerV2HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("--- DispatcherServletV3 ---");

        Object handler = requestMapping.getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        HandlerAdapter adapter = getHandlerAdapter(handler);
        ModelAndView modelAndView;
        try {
            modelAndView = adapter.handle(request, response, handler);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String viewName = modelAndView.getViewName();
        if (viewName == null) return;
        View view = getView(viewName);
        view.render(request, response, modelAndView.getModel());
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        for (HandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private View getView(String viewName) {
        if (viewName.equals(JSON_VIEW_PREFIX)) {
            return new JsonView();
        }
        boolean isRedirect = viewName.startsWith(REDIRECT_PREFIX);
        return jspViewResolver(viewName, isRedirect);
    }

    private JspView jspViewResolver(String viewName, boolean isRedirect) {
        if (isRedirect) {
            return new JspView(viewName.substring(REDIRECT_PREFIX.length()), true);
        }
        return new JspView("/WEB-INF/" + viewName + ".jsp", false);
    }

}
