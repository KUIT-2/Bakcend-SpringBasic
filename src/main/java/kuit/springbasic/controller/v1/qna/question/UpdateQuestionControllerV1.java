package kuit.springbasic.controller.v1.qna.question;

import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class UpdateQuestionControllerV1 implements ControllerV1 {

    private boolean isLoggedIn;
    private User userFromSession;

    private final MemoryQuestionRepository memoryQuestionRepository = MemoryQuestionRepository.getInstance();

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public void setUserFromSession(User user) {
        this.userFromSession = user;
    }

    @Override
    public ModelAndView execute(Map<String, String> params) throws SQLException {
        log.info("UpdateQuestionControllerV1");

        if (!isLoggedIn) {
            return new ModelAndView("redirect:/v1/user/loginForm");
        }

        int questionId = Integer.parseInt(params.get("questionId"));
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        if (!question.isSameUser(Objects.requireNonNull(userFromSession))) {
            throw new IllegalArgumentException();
        }

        String title = params.get("title");
        String contents = params.get("contents");
        question.updateTitleAndContents(title, contents);
        memoryQuestionRepository.update(question);

        return new ModelAndView("redirect:/v1");
    }

}
