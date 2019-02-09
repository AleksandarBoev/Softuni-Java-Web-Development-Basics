package app.web.servlets;

import app.domain.utils.MyFileReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    private static final String HOME_HTML_FULL_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\05.IntroductionToJavaEE\\src\\main\\resources\\views\\home.html";

    private MyFileReader myFileReader;

    @Inject
    public HomeServlet(MyFileReader myFileReader) {
        this.myFileReader = myFileReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String homeHtmlContent = myFileReader.readFileContentFromFullPath(HOME_HTML_FULL_PATH);

        resp.getWriter().println(homeHtmlContent);
    }
}
