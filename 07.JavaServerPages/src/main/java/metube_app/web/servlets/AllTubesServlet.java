package metube_app.web.servlets;

import metube_app.domain.models.view.AllTubesModel;
import metube_app.services.TubeService;
import metube_app.utils.MyModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/all-tubes")
public class AllTubesServlet extends HttpServlet {
    private TubeService tubeService;
    private MyModelMapper myModelMapper;

    @Inject
    public AllTubesServlet(TubeService tubeService, MyModelMapper myModelMapper) {
        this.tubeService = tubeService;
        this.myModelMapper = myModelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AllTubesModel> allTubesModels = this.tubeService.findAll()
                .stream()
                .map(t -> this.myModelMapper.map(t, AllTubesModel.class))
                .collect(Collectors.toList());

        req.setAttribute("all_tubes", allTubesModels);
        req.getRequestDispatcher("jsps/all-tubes.jsp").forward(req, resp);
    }
}
