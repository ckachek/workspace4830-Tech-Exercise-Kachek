import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchKachek")
public class SearchKachek extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchKachek() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      String keyword2 = request.getParameter("keyword2");
      String keyword3 = request.getParameter("keyword3");
      search(keyword, keyword2, keyword3, response);
   }

   void search(String keyword, String keyword2, String keyword3, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionKachek.getDBConnection(getServletContext());
         connection = DBConnectionKachek.connection;

         if (keyword.isEmpty() && keyword2.isEmpty() && keyword3.isEmpty()) {
            String selectSQL = "SELECT * FROM test";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else if (!keyword.isEmpty() && keyword2.isEmpty() && keyword3.isEmpty()) {
            String selectSQL = "SELECT * FROM test WHERE TASK LIKE ?";
            String theTask = keyword;
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theTask);
         } else if (keyword.isEmpty() && !keyword2.isEmpty() && keyword3.isEmpty()) {
             String selectSQL = "SELECT * FROM test WHERE DUE LIKE ?";
             String theDue = keyword2;
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theDue);
         } else if (keyword.isEmpty() && keyword2.isEmpty() && !keyword3.isEmpty()) {
             String selectSQL = "SELECT * FROM test WHERE PRIORITY LIKE ?";
             String thePriority = keyword3;
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, thePriority);
         } else if (!keyword.isEmpty() && !keyword2.isEmpty() && keyword3.isEmpty()) {
             String selectSQL = "SELECT * FROM test WHERE TASK LIKE ? and DUE LIKE ?";
             String theTask = keyword;
             String theDue = keyword2;
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theTask);
             preparedStatement.setString(2, theDue);
         } else if (!keyword.isEmpty() && keyword2.isEmpty() && !keyword3.isEmpty()) {
             String selectSQL = "SELECT * FROM test WHERE TASK LIKE ? and PRIORITY LIKE ?";
             String theTask = keyword;
             String thePriority = keyword3;
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theTask);
             preparedStatement.setString(2, thePriority);
         } else if (keyword.isEmpty() && !keyword2.isEmpty() && !keyword3.isEmpty()) {
             String selectSQL = "SELECT * FROM test WHERE DUE LIKE ? and PRIORITY LIKE ?";
             String theDue = keyword2;
             String thePriority = keyword3;
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theDue);
             preparedStatement.setString(2, thePriority);
         } else {
             String selectSQL = "SELECT * FROM test WHERE TASK LIKE ? and DUE LIKE ? and PRIORITY LIKE ?";
             String theTask = keyword;
             String theDue = keyword2;
             String thePriority = keyword3;
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theTask);
             preparedStatement.setString(2, theDue);
             preparedStatement.setString(3, thePriority);
         }
         
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String task = rs.getString("task").trim();
            String due = rs.getString("due").trim();
            String priority = rs.getString("priority").trim();
            String notes = rs.getString("notes").trim();

            if ((keyword.isEmpty() && keyword2.isEmpty() && keyword3.isEmpty()) || (task.contains(keyword) && keyword2.isEmpty() && keyword3.isEmpty()) || (keyword.isEmpty() && due.contains(keyword2) && keyword3.isEmpty()) 
            		|| (keyword.isEmpty() && keyword2.isEmpty() && priority.contains(keyword3)) || (task.contains(keyword) && due.contains(keyword2) && keyword3.isEmpty()) || (task.contains(keyword) && keyword2.isEmpty() && priority.contains(keyword3)) 
            		|| (keyword.isEmpty() && due.contains(keyword2) && priority.contains(keyword3)) || (task.contains(keyword) && due.contains(keyword2) && priority.contains(keyword3))) {
               out.println("ID: " + id + ", ");
               out.println("Task: " + task + ", ");
               out.println("Due Date: " + due + ", ");
               out.println("Priority: " + priority + ", ");
               out.println("Notes: " + notes + "<br>");
            }
         }
         out.println("<a href=/webproject-tech-exercise-kachek/search_kachek.html>Return</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
