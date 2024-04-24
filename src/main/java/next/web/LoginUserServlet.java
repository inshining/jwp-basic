package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        if(user == null){
            res.sendRedirect("/user/login_failed.html");
            return;
        }
        if(user.getPassword().equals(password)){
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            res.sendRedirect("/index.html");
        }else{
            res.sendRedirect("/user/login_failed.html");
        }
    }
}
