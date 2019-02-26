package app.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
If a guest (not logged in) user tries to access the pages below,
redirect him/her/apache-helicopter to the "/index" page
 */
@WebFilter({
        "/home", "/profile", "/friends", "/profile/*"
})
public class GuestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getSession().getAttribute("username") == null) {
            httpServletResponse.sendRedirect("/index");
        } else {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
