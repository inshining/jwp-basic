package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddQuestionController.class);
    private static final String UserLoginJsp = "/user/login.jsp";

    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Question question = new Question(req.getParameter("writer"), req.getParameter("title"), req.getParameter("contents"));
        log.debug("question : {}", question);
        if (!isLogin(req)){
            return jspView(UserLoginJsp);
        }
        Question savedQuestion = questionDao.insert(question);
        return jspView("redirect:/");
    }

    private boolean isLogin(HttpServletRequest req) {
        HttpSession session =req.getSession();
        return session.getAttribute("user") != null;
    }
}
