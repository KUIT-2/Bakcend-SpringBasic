package kuit.springbasic.controller.qna;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.domain.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final MemoryAnswerRepository memoryAnswerRepository;

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    //js 파일에서 ajax 이용
    @ResponseBody
    @PostMapping("/api/qna/addAnswer")
    public Answer addAnswer(@ModelAttribute Answer answer) {
        memoryAnswerRepository.insert(answer);
        return answer;
    }
}
