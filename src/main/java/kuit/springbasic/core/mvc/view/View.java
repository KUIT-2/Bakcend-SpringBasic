package kuit.springbasic.core.mvc.view;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public interface View {

    void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException, ServletException;

}
