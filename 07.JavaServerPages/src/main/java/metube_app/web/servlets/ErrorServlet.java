package metube_app.web.servlets;

import metube_app.domain.enums.HttpError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int error = Integer.parseInt(req.getQueryString().split("code=")[1]);
        req.setAttribute("error", HttpError.getError(error));
        req.getRequestDispatcher("jsps/error.jsp").forward(req, resp);
    }
}
