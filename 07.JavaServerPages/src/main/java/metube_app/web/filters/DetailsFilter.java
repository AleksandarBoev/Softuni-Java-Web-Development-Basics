package metube_app.web.filters;

import metube_app.domain.enums.HttpError;
import metube_app.domain.models.service.TubeServiceModel;
import metube_app.services.TubeService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/details")
public class DetailsFilter implements Filter {
    private TubeService tubeService;

    @Inject
    public DetailsFilter(TubeService tubeService) {
        this.tubeService = tubeService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String queryString = ((HttpServletRequest) request).getQueryString();

        if (!queryString.contains("name=")) {
            //httpRequest.setAttribute("error", HttpError.getError(400)); //problematic code
            ((HttpServletResponse)response).sendRedirect("/error?code=400");
            //chain.doFilter(request, response); //problematic code
            return;
        }

        String tubeName = queryString.split("name=")[1];
        TubeServiceModel tubeServiceModel = this.tubeService.findByName(tubeName);

        if (tubeServiceModel == null) {
//            httpRequest.setAttribute("error", HttpError.getError(404)); problematic
            ((HttpServletResponse)response).sendRedirect("/error?code=404");
//            chain.doFilter(request, response); //problematic
            return;
            //chain.doFilter(request, response);
        }

        httpRequest.setAttribute("tube_service_model", tubeServiceModel);

        chain.doFilter(request, response);
    }
}
