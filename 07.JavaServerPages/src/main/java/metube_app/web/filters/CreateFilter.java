package metube_app.web.filters;

import metube_app.domain.models.view.TubeCreateModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/create")
public class CreateFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if ("post".equals(httpRequest.getMethod().toLowerCase())) {
            TubeCreateModel tubeCreateModel = new TubeCreateModel();
            tubeCreateModel.setName(httpRequest.getParameter("title"));
            tubeCreateModel.setDescription(httpRequest.getParameter("description"));
            tubeCreateModel.setUploader(httpRequest.getParameter("uploader"));
            tubeCreateModel.setYoutubeLink(httpRequest.getParameter("link"));
            httpRequest.setAttribute("binding_model_create_tube", tubeCreateModel);
        }

        chain.doFilter(request, response);
    }
}
