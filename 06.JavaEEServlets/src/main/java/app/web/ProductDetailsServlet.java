package app.web;

import app.domain.models.views.ProductDetailsView;
import app.services.ProductService;
import app.utils.MyFileReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/details")
public class ProductDetailsServlet extends HttpServlet {
    private static final String DETAILS_HTML_FULL_FILE_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\06.JavaEEServlets\\src\\main\\resources\\views\\details.html";

    private MyFileReader myFileReader;
    private ProductService productService;

    @Inject
    public ProductDetailsServlet(MyFileReader myFileReader, ProductService productService) {
        this.myFileReader = myFileReader;
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = this.processQueryString(req).get("name");

        ProductDetailsView productDetailsView = this.productService.getProductDetailsView(productName);

        String htmlContent = this.myFileReader.readFileContentFromFullPath(DETAILS_HTML_FULL_FILE_PATH);
        htmlContent = htmlContent.replace("{{name}}", productDetailsView.getName())
                .replace("{{description}}", productDetailsView.getDescription())
                .replace("{{type}}", productDetailsView.getType());

        resp.getWriter().println(htmlContent);
    }

    private Map<String, String> processQueryString(HttpServletRequest req) {
        Map<String, String> result = new HashMap<>();
        String[] kvpValues = req.getQueryString().split("&");
        for (String kvpValue : kvpValues) {
            result.put(kvpValue.split("=")[0], kvpValue.split("=")[1]);
        }

        return result;
    }
}
