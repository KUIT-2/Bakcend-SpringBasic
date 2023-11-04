package kuit.springbasic.controller.qna;


import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnAController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;

    /**
     * TODO: showQnA
     */
    @RequestMapping("/qna/show")
    public String showQnA(@RequestParam("questionId") String questionId, Model model) {
        log.info("QnAController.showQnA");
        int id = Integer.parseInt(questionId);

        model.addAttribute("question", memoryQuestionRepository.findByQuestionId(id));
        model.addAttribute("answers", memoryAnswerRepository.findAllByQuestionId(id));
        return "/qna/show";
    }

}
