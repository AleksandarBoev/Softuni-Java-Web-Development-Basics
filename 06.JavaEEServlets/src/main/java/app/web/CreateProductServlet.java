package app.web;

import app.domain.enums.ProductType;
import app.services.ProductService;
import app.utils.MyFileReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/create/product")
public class CreateProductServlet extends HttpServlet {
    private static final String CREATE_PRODUCT_HTML_FULL_FILE_PATH =
            "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\06.JavaEEServlets\\src\\main\\resources\\views\\create-product.html";

    private MyFileReader myFileReader;
    private ProductService productService;
    private String options;

    @Override
    public void init() throws ServletException {
        /*
        Writing options logic here, because changes made to ProductType enum will take effect only after
        redeployment of server. This method is executed only once per redeployment.
         */
        String[] allOptions = Arrays.stream(ProductType.values())
                .map(type -> String.format("<option value=\"%1$s\">%1$s</option>", type.toString()))
                .toArray(n-> new String[n]);

        StringBuilder optionsConcatenated = new StringBuilder();
        for (String option : allOptions) {
            optionsConcatenated.append(option).append(System.lineSeparator());
        }

        this.options = optionsConcatenated.toString().trim();
    }

    @Inject
    public CreateProductServlet(MyFileReader myFileReader, ProductService productService) {
        this.myFileReader = myFileReader;
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String htmlContent = this.myFileReader.readFileContentFromFullPath(CREATE_PRODUCT_HTML_FULL_FILE_PATH);

        htmlContent = htmlContent.replace("{{options}}", this.options);

        resp.getWriter().println(htmlContent);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("name");
        String productDescription = req.getParameter("description");
        String type = req.getParameter("type");
    }
}
