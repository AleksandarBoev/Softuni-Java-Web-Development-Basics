package app.web;

import app.domain.entities.Student;
import app.domain.utils.MyFileReader;
import app.repositories.GenericRepository;
import app.repositories.StudentRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    private StudentRepository studentRepository;

    @Inject
    public HomeServlet(MyFileReader myFileReader, StudentRepository studentRepository) {
        this.myFileReader = myFileReader;
        this.studentRepository = studentRepository;
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = this.studentRepository.getAll();

        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append(this.envelopeInLi(student.toString())).append(System.lineSeparator());
        }

        String htmlContent = this.myFileReader.readFileContentFromFullPath("C:\\AleksandarUser\\Programming\\GitHubRepositories\\Softuni-Java-Web-Development-Basics\\06.JavaEEServlets\\src\\main\\resources\\views\\index.html");

        htmlContent = htmlContent.replace("{{students}}", sb.toString().trim());

        resp.getWriter().println(htmlContent);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();

        student.setName(req.getParameter("name"));
        student.setTownName(req.getParameter("town"));

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("practise_persistence");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(student);

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        resp.sendRedirect("/");
    }

    private String envelopeInLi(String someString) {
        return "<li>" + someString + "</li>";
    }
}
