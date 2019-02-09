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

@WebServlet("/cats/all")
public class AllCatsServlet extends HttpServlet {
    //TODO would be easier to make a constant class which contains all html file texts in a map and gets them via reflection from teh views package
    private static final String ALL_CATS_HTML_FULL_FILE_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\05.IntroductionToJavaEE\\src\\main\\resources\\views\\all-cats.html";

    private MyFileReader myFileReader;

    @Inject
    public AllCatsServlet(MyFileReader myFileReader) {
        this.myFileReader = myFileReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Cat> allCats = (HashMap<String, Cat>) req.getSession().getAttribute("allCats");

        String allCatsHtmlContent = this.myFileReader.readFileContentFromFullPath(ALL_CATS_HTML_FULL_FILE_PATH);

        if (allCats == null || allCats.isEmpty()) { //what if 0 cats were created? Then there would be no such map in the first place
            resp.getWriter().println(
                    allCatsHtmlContent.replace(
                            "{{cats}}",
                            "There are no cats." + this.createHyperLink("/cats/create", "Create some!") + "<br/>")
            );
        } else {
            StringBuilder replacement = new StringBuilder();
            for (Cat cat : allCats.values()) {
                String catName = cat.getName();
                String path = this.createPathWithQueryString(catName);
                replacement.append(this.createHyperLink(path, catName)).append("<br/>");
            }

            String resultHtml = allCatsHtmlContent.replace("{{cats}}", replacement.toString());
            resp.getWriter().println(resultHtml);
        }
    }

    private String createHyperLink(String path, String text) {
        return String.format("<a href=\"%s\">%s</a>", path, text);
    }

    private String createPathWithQueryString(String catName) {
        return "/cats/profile?name=" + catName;
    }
}
