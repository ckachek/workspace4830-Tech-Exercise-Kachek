
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteKachek")
public class DeleteKachek extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public DeleteKachek() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String id = request.getParameter("id");

      Connection connection = null;
      String deleteSql = "DELETE FROM test WHERE id=?";

      try {
         DBConnectionKachek.getDBConnection(getServletContext());
         connection = DBConnectionKachek.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(deleteSql);
         preparedStmt.setString(1, id);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Item Deleted";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //
            
            "</ul>\n");

      out.println("<a href=/webproject-tech-exercise-kachek/delete_kachek.html>Return</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}