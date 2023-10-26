package kuit.springbasic.core.mvc.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class JspView implements View {

    private String viewPath;
    private boolean isRedirect;

    public JspView(String viewPath, boolean isRedirect) {
        this.viewPath = viewPath;
        this.isRedirect = isRedirect;
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)
            throws ServletException, IOException {

        if (isRedirect) {
            response.sendRedirect(viewPath);
            return;
        }
        setModelToRequestAttribute(model, request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
        requestDispatcher.forward(request, response);
    }

    private void setModelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach(request::setAttribute);
    }

}
