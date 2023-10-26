package kuit.springbasic.core.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class JsonView implements View {

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;charset=UTF-8";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)
            throws IOException {
        log.info("JsonView");

        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        PrintWriter out = response.getWriter();
        for (Object object : model.values()) {
            out.print(mapper.writeValueAsString(object));
        }
    }

}
