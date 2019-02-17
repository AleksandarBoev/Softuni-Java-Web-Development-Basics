package metube_app.web.servlets;

import metube_app.domain.models.service.TubeServiceModel;
import metube_app.domain.models.view.TubeCreateModel;
import metube_app.services.TubeService;
import metube_app.utils.MyModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private TubeService tubeService;
    private MyModelMapper myModelMapper;

    @Inject
    public CreateServlet(TubeService tubeService, MyModelMapper myModelMapper) {
        this.tubeService = tubeService;
        this.myModelMapper = myModelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsps/create_tube.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TubeCreateModel tubeCreateModel = (TubeCreateModel) req.getAttribute("binding_model_create_tube");
        TubeServiceModel tubeServiceModel = this.myModelMapper.map(tubeCreateModel, TubeServiceModel.class);

        this.tubeService.save(tubeServiceModel);

        resp.sendRedirect("/details?name=" + tubeServiceModel.getName());
    }
}
