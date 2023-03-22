
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertKachek")
public class InsertKachek extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertKachek() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String task = request.getParameter("task");
      String due = request.getParameter("due");
      String priority = request.getParameter("priority");
      String notes = request.getParameter("notes");

      Connection connection = null;
      String insertSql = " INSERT INTO test (id, TASK, DUE, PRIORITY, NOTES) values (default, ?, ?, ?, ?)";

      try {
         DBConnectionKachek.getDBConnection(getServletContext());
         connection = DBConnectionKachek.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         if (task.isEmpty()) {
        	 task = "Task";
         }
         preparedStmt.setString(1, task);
         preparedStmt.setString(2, due);
         if (priority.isEmpty()) {
        	 priority = "1";
         }
         preparedStmt.setString(3, priority);
         preparedStmt.setString(4, notes);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Task</b>: " + task + "\n" + //
            "  <li><b>Due Date</b>: " + due + "\n" + //
            "  <li><b>Priority</b>: " + priority + "\n" + //
            "  <li><b>Notes</b>: " + notes + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-tech-exercise-kachek/insert_kachek.html>Return</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}