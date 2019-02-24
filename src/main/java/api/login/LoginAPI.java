package api.login;

import constants.API;
import constants.Keys;
import entity.User;
import filters.LoginFilter;
import services.LanguageBase;
import services.answers.ErrorAnswer;
import services.answers.IAnswer;
import services.answers.SuccessAnswer;
import services.hibernate.Hibernator;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
@WebServlet(API.LOGIN)
public class LoginAPI extends HttpServlet {

    private static final Hibernator hibernator = Hibernator.getInstance();
    private static final LanguageBase lang = LanguageBase.getBase();
    public static final String NO_USER = "login.error.no.user";
    public static final String WRONG_PASSWORD = "login.error.wrong.password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        String language = "ru";

        IAnswer answer;
        User user = hibernator.get(User.class, "email", body.get("login"));

        if (user == null){
            answer = new ErrorAnswer();
            answer.add("msg", lang.get(language, NO_USER));
        } else if (!user.getPassword().equals(body.get("password"))){
            answer = new ErrorAnswer();
            answer.add("msg", lang.get(language, WRONG_PASSWORD));
        } else {
            answer = new SuccessAnswer();
            req.getSession().setAttribute("uid", user.getId());
            req.getSession().setAttribute("token", LoginFilter.addUser(user));
        }

        body.clear();
        PostUtil.write(resp, JsonParser.toJson(answer).toJSONString());

    }
}
