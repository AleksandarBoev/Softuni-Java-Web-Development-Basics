package app.web.servlets;

import app.domain.entities.Cat;
import app.domain.utils.MyFileReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cats/create")
public class CatCreateServlet extends HttpServlet {
    private static final String CAT_CREATE_HTML_FULL_FILE_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\05.IntroductionToJavaEE\\src\\main\\resources\\views\\cat-create.html";
    private static final String CAT_PROFILE_HTML_FULL_FILE_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\05.IntroductionToJavaEE\\src\\main\\resources\\views\\cat-profile.html";

    private MyFileReader myFileReader;

    @Inject
    public CatCreateServlet(MyFileReader myFileReader) {
        this.myFileReader = myFileReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String catCreateHtmlContent = this.myFileReader.readFileContentFromFullPath(CAT_CREATE_HTML_FULL_FILE_PATH);

        resp.getWriter().println(catCreateHtmlContent);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cat cat = new Cat();

        cat.setName(req.getParameter("catName"));
        cat.setBreed(req.getParameter("catBreed"));
        cat.setColor(req.getParameter("catColor"));
        cat.setAge(Integer.parseInt(req.getParameter("catAge")));

        if (req.getSession().getAttribute("allCats") == null) {
            req.getSession().setAttribute("allCats", new HashMap<String, Cat>());
        }

        Map<String, Cat> allCats = (HashMap<String, Cat>) req.getSession().getAttribute("allCats");

        allCats.put(cat.getName(), cat);

        String queryString = "name=" + cat.getName();
        resp.sendRedirect("profile?" + queryString);
    }
}
