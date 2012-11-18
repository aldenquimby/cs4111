package courseworks;

import courseworks.model.Professor;
import courseworks.model.Student;
import courseworks.sql.CourseworksWriter;
import courseworks.sql.ICourseworksWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "SigninServlet")
public class SigninServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter pw = new PrintWriter(response.getOutputStream());

        final String req_uni = request.getParameter("uni");
        final String req_type = request.getParameter("type");
        final String req_name = request.getParameter("name");

        ICourseworksWriter writer = new CourseworksWriter();

        boolean succeeded = false;

        if ("Professor".equals(req_type)) {
            Professor p = new Professor(){{name=req_name; uni=req_uni;}};

            succeeded = writer.createProfessor(p);

            if (succeeded) {
                pw.print("/courseworks/courses.jsp?prof_uni=" + req_uni);
                request.getSession().setAttribute(SessionKeys.logged_in_prof, p);
            }
        }
        else if ("Student".equals(req_type)) {
            Student s = new Student(){{name=req_name; uni=req_uni;}};

            succeeded = writer.createStudent(s);

            if (succeeded) {
                pw.print("/courseworks/coursepage.jsp?student_uni=" + req_uni);
                request.getSession().setAttribute(SessionKeys.logged_in_student, s);
            }
        }

        if (!succeeded){
            response.sendError(500);
        }

        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String req_uni = request.getParameter("uni");
        final String req_name = request.getParameter("name");
        final String req_type = request.getParameter("type");

        PrintWriter pw = new PrintWriter(response.getOutputStream());

        if ("Professor".equals(req_type)) {
            pw.print("/courseworks/courses.jsp?prof_uni=" + req_uni);
            request.getSession().setAttribute(SessionKeys.logged_in_prof, new Professor(){{name=req_name; uni=req_uni;}});
        }
        else if ("Student".equals(req_type)) {
            pw.print("/courseworks/coursepage.jsp?student_uni=" + req_uni);
            request.getSession().setAttribute(SessionKeys.logged_in_student, new Student(){{name=req_name; uni=req_uni;}});
        }
        else {
            pw.print("/courseworks/index.jsp");
        }

        pw.close();
    }

}