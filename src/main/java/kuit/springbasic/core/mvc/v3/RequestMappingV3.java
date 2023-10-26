package kuit.springbasic.core.mvc.v3;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.controller.v3.ForwardControllerV1;
import kuit.springbasic.controller.v3.HomeControllerV1;
import kuit.springbasic.controller.v3.login.LogInControllerV1;
import kuit.springbasic.controller.v3.login.LogOutControllerV1;
import kuit.springbasic.controller.v3.qna.QnAFormControllerV1;
import kuit.springbasic.controller.v3.qna.ShowQnAControllerV1;
import kuit.springbasic.controller.v3.qna.answer.AddAnswerControllerV1;
import kuit.springbasic.controller.v3.qna.question.AddQuestionControllerV1;
import kuit.springbasic.controller.v3.qna.question.UpdateQuestionControllerV1;
import kuit.springbasic.controller.v3.qna.question.UpdateQuestionFormControllerV1;
import kuit.springbasic.controller.v3.user.CreateUserControllerV1;
import kuit.springbasic.controller.v3.user.UpdateUserControllerV1;
import kuit.springbasic.controller.v3.user.UpdateUserFormControllerV1;
import kuit.springbasic.controller.v3.user.UserListControllerV1;
import kuit.springbasic.controller.v3.ForwardControllerV2;
import kuit.springbasic.controller.v3.HomeControllerV2;
import kuit.springbasic.controller.v3.login.LogInControllerV2;
import kuit.springbasic.controller.v3.login.LogOutControllerV2;
import kuit.springbasic.controller.v3.qna.QnAFormControllerV2;
import kuit.springbasic.controller.v3.qna.ShowQnAControllerV2;
import kuit.springbasic.controller.v3.qna.answer.AddAnswerControllerV2;
import kuit.springbasic.controller.v3.qna.question.AddQuestionControllerV2;
import kuit.springbasic.controller.v3.qna.question.UpdateQuestionControllerV2;
import kuit.springbasic.controller.v3.qna.question.UpdateQuestionFormControllerV2;
import kuit.springbasic.controller.v3.user.CreateUserControllerV2;
import kuit.springbasic.controller.v3.user.UpdateUserControllerV2;
import kuit.springbasic.controller.v3.user.UpdateUserFormControllerV2;
import kuit.springbasic.controller.v3.user.UserListControllerV2;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestMappingV3 {

    private final Map<String, Object> handlers = new HashMap<>();

    public RequestMappingV3() {
        initHandlers();
    }

    private void initHandlers() {
        // v1
        handlers.put("/v3/v1", new HomeControllerV1());

        handlers.put("/v3/v1/user/form", new ForwardControllerV1("/v3/v1/user/form"));
        handlers.put("/v3/v1/user/loginForm", new ForwardControllerV1("/v3/v1/user/login"));
        handlers.put("/v3/v1/user/loginFailed", new ForwardControllerV1("/v3/v1/user/loginFailed"));

        handlers.put("/v3/v1/user/login", new LogInControllerV1());
        handlers.put("/v3/v1/user/logout", new LogOutControllerV1());

        handlers.put("/v3/v1/user/signup", new CreateUserControllerV1());

        handlers.put("/v3/v1/user/updateForm", new UpdateUserFormControllerV1());
        handlers.put("/v3/v1/user/update", new UpdateUserControllerV1());

        handlers.put("/v3/v1/user/list", new UserListControllerV1());

        handlers.put("/v3/v1/qna/form", new QnAFormControllerV1());
        handlers.put("/v3/v1/qna/show", new ShowQnAControllerV1());

        handlers.put("/v3/v1/qna/create", new AddQuestionControllerV1());
        handlers.put("/v3/v1/qna/updateForm", new UpdateQuestionFormControllerV1());
        handlers.put("/v3/v1/qna/update", new UpdateQuestionControllerV1());

        handlers.put("/v3/v1/api/qna/addAnswer", new AddAnswerControllerV1());

        // v2
        handlers.put("/v3/v2", new HomeControllerV2());

        handlers.put("/v3/v2/user/form", new ForwardControllerV2("/v3/v2/user/form"));
        handlers.put("/v3/v2/user/loginForm", new ForwardControllerV2("/v3/v2/user/login"));
        handlers.put("/v3/v2/user/loginFailed", new ForwardControllerV2("/v3/v2/user/loginFailed"));

        handlers.put("/v3/v2/user/login", new LogInControllerV2());
        handlers.put("/v3/v2/user/logout", new LogOutControllerV2());

        handlers.put("/v3/v2/user/signup", new CreateUserControllerV2());

        handlers.put("/v3/v2/user/updateForm", new UpdateUserFormControllerV2());
        handlers.put("/v3/v2/user/update", new UpdateUserControllerV2());

        handlers.put("/v3/v2/user/list", new UserListControllerV2());

        handlers.put("/v3/v2/qna/form", new QnAFormControllerV2());
        handlers.put("/v3/v2/qna/show", new ShowQnAControllerV2());

        handlers.put("/v3/v2/qna/create", new AddQuestionControllerV2());
        handlers.put("/v3/v2/qna/updateForm", new UpdateQuestionFormControllerV2());
        handlers.put("/v3/v2/qna/update", new UpdateQuestionControllerV2());

        handlers.put("/v3/v2/api/qna/addAnswer", new AddAnswerControllerV2());
    }

    public Object getHandler(HttpServletRequest request) {
        log.info("requestURI={}", request.getRequestURI());
        return handlers.get(request.getRequestURI());
    }

}
