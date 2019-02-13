package app.web;

import app.domain.models.services.ProductServiceModel;
import app.domain.models.views.ProductAllViewModel;
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
import java.util.stream.Collectors;


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
        List<ProductAllViewModel> allProducts = this.productService.getProductsAllViewModels()
                .stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        for (ProductAllViewModel product : allProducts) {
            sb.append(this.createLiHyperlink(product.getName())).append(System.lineSeparator());
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

    private String createLiHyperlink(String someString) {
        return String.format("<li><a href=\"/details?name=%1$s\">%1$s</a></li>", someString);
    }
}
