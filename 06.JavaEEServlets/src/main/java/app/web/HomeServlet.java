package app.web;

import app.domain.entities.Product;
import app.domain.models.services.ProductServiceModel;
import app.services.ProductService;
import app.utils.MyFileReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/")
public class HomeServlet extends HttpServlet {
    private MyFileReader myFileReader;
    private ProductService productService;

    @Inject
    public HomeServlet(MyFileReader myFileReader, ProductService productService) {
        this.myFileReader = myFileReader;
        this.productService = productService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> allProducts = this.productService.getProducts();

        StringBuilder sb = new StringBuilder();

        for (Product product : allProducts) {
            sb.append(this.envelopeInLi(product.toString())).append(System.lineSeparator()); //TODO change with ModelView!
        }

        String htmlContent = this.myFileReader.readFileContentFromFullPath
                ("C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\06.JavaEEServlets\\src\\main\\resources\\views\\index.html");

        resp.setContentType("text/html");
        resp.getWriter().println(htmlContent.replace("{{products}}", sb.toString().trim()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductServiceModel productServiceModel = new ProductServiceModel();
        productServiceModel.setName(req.getParameter("name"));
        productServiceModel.setDescription(req.getParameter("description"));
        productServiceModel.setType(req.getParameter("type"));

        this.productService.save(productServiceModel);
        resp.sendRedirect("/");
    }

    private String envelopeInLi(String someString) {
        return "<li>" + someString + "</li>";
    }
}
