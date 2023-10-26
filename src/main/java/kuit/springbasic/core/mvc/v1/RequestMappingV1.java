package kuit.springbasic.core.mvc.v1;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.controller.v1.ForwardControllerV1;
import kuit.springbasic.controller.v1.HomeControllerV1;
import kuit.springbasic.controller.v1.login.LogInControllerV1;
import kuit.springbasic.controller.v1.login.LogOutControllerV1;
import kuit.springbasic.controller.v1.qna.QnAFormControllerV1;
import kuit.springbasic.controller.v1.qna.ShowQnAControllerV1;
import kuit.springbasic.controller.v1.qna.answer.AddAnswerControllerV1;
import kuit.springbasic.controller.v1.qna.question.AddQuestionControllerV1;
import kuit.springbasic.controller.v1.qna.question.UpdateQuestionControllerV1;
import kuit.springbasic.controller.v1.qna.question.UpdateQuestionFormControllerV1;
import kuit.springbasic.controller.v1.user.CreateUserControllerV1;
import kuit.springbasic.controller.v1.user.UpdateUserControllerV1;
import kuit.springbasic.controller.v1.user.UpdateUserFormControllerV1;
import kuit.springbasic.controller.v1.user.UserListControllerV1;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestMappingV1 {

    private final Map<String, ControllerV1> controllers = new HashMap<>();

    public RequestMappingV1() {
        initControllers();
    }

    private void initControllers() {
        controllers.put("/v1", new HomeControllerV1());

        controllers.put("/v1/user/form", new ForwardControllerV1("/v1/user/form"));
        controllers.put("/v1/user/loginForm", new ForwardControllerV1("/v1/user/login"));
        controllers.put("/v1/user/loginFailed", new ForwardControllerV1("/v1/user/loginFailed"));

        controllers.put("/v1/user/login", new LogInControllerV1());
        controllers.put("/v1/user/logout", new LogOutControllerV1());

        controllers.put("/v1/user/signup", new CreateUserControllerV1());

        controllers.put("/v1/user/updateForm", new UpdateUserFormControllerV1());
        controllers.put("/v1/user/update", new UpdateUserControllerV1());

        controllers.put("/v1/user/list", new UserListControllerV1());

        controllers.put("/v1/qna/form", new QnAFormControllerV1());
        controllers.put("/v1/qna/show", new ShowQnAControllerV1());

        controllers.put("/v1/qna/create", new AddQuestionControllerV1());
        controllers.put("/v1/qna/updateForm", new UpdateQuestionFormControllerV1());
        controllers.put("/v1/qna/update", new UpdateQuestionControllerV1());

        controllers.put("/v1/api/qna/addAnswer", new AddAnswerControllerV1());
    }

    public ControllerV1 getController(HttpServletRequest request) {
        log.info("requestURI={}", request.getRequestURI());
        return controllers.get(request.getRequestURI());
    }

}
