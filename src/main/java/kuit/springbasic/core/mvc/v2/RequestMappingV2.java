package kuit.springbasic.core.mvc.v2;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.controller.v2.ForwardControllerV2;
import kuit.springbasic.controller.v2.HomeControllerV2;
import kuit.springbasic.controller.v2.login.LogInControllerV2;
import kuit.springbasic.controller.v2.login.LogOutControllerV2;
import kuit.springbasic.controller.v2.qna.QnAFormControllerV2;
import kuit.springbasic.controller.v2.qna.ShowQnAControllerV2;
import kuit.springbasic.controller.v2.qna.answer.AddAnswerControllerV2;
import kuit.springbasic.controller.v2.qna.question.AddQuestionControllerV2;
import kuit.springbasic.controller.v2.qna.question.UpdateQuestionControllerV2;
import kuit.springbasic.controller.v2.qna.question.UpdateQuestionFormControllerV2;
import kuit.springbasic.controller.v2.user.CreateUserControllerV2;
import kuit.springbasic.controller.v2.user.UpdateUserControllerV2;
import kuit.springbasic.controller.v2.user.UpdateUserFormControllerV2;
import kuit.springbasic.controller.v2.user.UserListControllerV2;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestMappingV2 {

    private final Map<String, ControllerV2> controllers = new HashMap<>();

    public RequestMappingV2() {
        initControllers();
    }

    private void initControllers() {
        controllers.put("/v2", new HomeControllerV2());

        controllers.put("/v2/user/form", new ForwardControllerV2("/v2/user/form"));
        controllers.put("/v2/user/loginForm", new ForwardControllerV2("/v2/user/login"));
        controllers.put("/v2/user/loginFailed", new ForwardControllerV2("/v2/user/loginFailed"));

        controllers.put("/v2/user/login", new LogInControllerV2());
        controllers.put("/v2/user/logout", new LogOutControllerV2());

        controllers.put("/v2/user/signup", new CreateUserControllerV2());

        controllers.put("/v2/user/updateForm", new UpdateUserFormControllerV2());
        controllers.put("/v2/user/update", new UpdateUserControllerV2());

        controllers.put("/v2/user/list", new UserListControllerV2());

        controllers.put("/v2/qna/form", new QnAFormControllerV2());
        controllers.put("/v2/qna/show", new ShowQnAControllerV2());

        controllers.put("/v2/qna/create", new AddQuestionControllerV2());
        controllers.put("/v2/qna/updateForm", new UpdateQuestionFormControllerV2());
        controllers.put("/v2/qna/update", new UpdateQuestionControllerV2());

        controllers.put("/v2/api/qna/addAnswer", new AddAnswerControllerV2());
    }

    public ControllerV2 getController(HttpServletRequest request) {
        log.info("requestURI={}", request.getRequestURI());
        return controllers.get(request.getRequestURI());
    }

}
