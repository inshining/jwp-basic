package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private AnswerDao answerDao = new AnswerDao();
    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);
        Question question = questionDao.findById(answer.getQuestionId());
        if (question == null) {
            throw new IllegalStateException("존재하지 않는 질문입니다.");
        }
        question.increaseCountOfComment();

        Answer savedAnswer = answerDao.insert(answer);
        questionDao.update(question);

        return jsonView().addObject("answer", savedAnswer);
    }
}
