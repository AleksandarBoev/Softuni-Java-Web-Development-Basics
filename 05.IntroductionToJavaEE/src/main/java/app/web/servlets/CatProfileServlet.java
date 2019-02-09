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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cats/profile")
public class CatProfileServlet extends HttpServlet {
    private static final String ERROR_HTML_FULL_FILE_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\05.IntroductionToJavaEE\\src\\main\\resources\\views\\cat-profile-error.html";
    private static final String CAT_PROFILE_HTML_FULL_FILE_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\05.IntroductionToJavaEE\\src\\main\\resources\\views\\cat-profile.html";
    private static final String CAT_NOT_FOUND = "Cat with name %s does not exist!";
    private static final String INCORRECT_QUERY_STRING = "Incorrect query string!";

    private MyFileReader myFileReader;

    @Inject
    public CatProfileServlet(MyFileReader myFileReader) {
        this.myFileReader = myFileReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> keyValues = this.processQueryString(req);
        if (keyValues == null || !keyValues.containsKey("name")) {
            resp.getWriter().println(
                    this.myFileReader.readFileContentFromFullPath(
                            ERROR_HTML_FULL_FILE_PATH).replace("{{errorMessage}}", INCORRECT_QUERY_STRING)
            );
            return;
        }

        Map<String, Cat> cats = (HashMap<String, Cat>)req.getSession().getAttribute("allCats");

        if (!cats.containsKey(keyValues.get("name"))) { //if cat is not in the map
            resp.getWriter().println(
                    this.myFileReader.readFileContentFromFullPath(
                            ERROR_HTML_FULL_FILE_PATH).replace("{{errorMessage}}", String.format(CAT_NOT_FOUND, keyValues.get("name")))
            );
        }

        Cat cat = cats.get(keyValues.get("name"));

        String catProfileHtmlContent = this.myFileReader.readFileContentFromFullPath(CAT_PROFILE_HTML_FULL_FILE_PATH);
        catProfileHtmlContent = catProfileHtmlContent
                .replace("{{catName}}", cat.getName())
                .replace("{{catBreed}}", cat.getBreed())
                .replace("{{catColor}}", cat.getColor())
                .replace("{{catAge}}", cat.getAge().toString());

        resp.getWriter().println(catProfileHtmlContent);
    }

    private Map<String, String> processQueryString(HttpServletRequest req) {
        String queryString = req.getQueryString();

        if (queryString == null)
            return null;

        Map<String, String> result = new HashMap<>();
        Arrays.stream(queryString.split("&"))
                .forEach(q -> {
                    result.put(q.split("=")[0], q.split("=")[1]);
                });

        return result;
    }
}
