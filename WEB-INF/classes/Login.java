import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Login extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println(
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Login</title>
                        </head>
                        <body>
                            <center>
                                <h1 style="text-align: center;">LOGIN</h1>
                                <div style="width: 400px; border: 2px solid black;">
                                    <form action="login" method="POST" style="padding: 30px; padding-bottom: 0px;">
                                        <label for="username">Username</label>
                                        <input style="display: block; width: 98%" type="text" name="username" id="username" placeholder="Enter admin username">
                                        <label for="password">Password</label>
                                        <input style="display: block; width: 98%" type="password" name="password" id="password" placeholder="Enter admin password">
                                        <button type="submit" style="margin: 20px 0px;">LOGIN</button>
                                    </form>
                                </div>
                            </center>
                        </body>
                        </html>
                                        """);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!username.equals("admin") || !password.equals("admin")) {
            writer.println("<script> alert('Invalid username/password');history.back();</script>");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        writer.println(
                """
                        <!DOCTYPE html>
                            <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <title>Login</title>
                                    <style>
                                        th,td{
                                            border: 1px dotted black;
                                        }
                                    </style>
                                </head>
                                <body>
                                    <center>
                                        <table style="width: 800px; border: 2px solid black; border-spacing: 0px;">
                                            <tr>
                                                <th>Name</th>
                                                <th>Email</th>
                                                <th>Category</th>
                                                <th>Description</th>
                                                <th>Complained At</th>
                                            </tr>
                                            """);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String pass = "root";

            Connection rootCon = DriverManager.getConnection(url, user, pass);
            Statement stmt = rootCon.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS complaint_system");
            stmt.close();
            rootCon.close();

            url = "jdbc:mysql://localhost:3306/complaint_system";

            Connection con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS complaints (id int auto_increment primary key, name varchar(64) not null, email varchar(64) not null, category varchar(128) not null, description varchar(256) not null, created_at timestamp default current_timestamp)");
            stmt.close();

            PreparedStatement ps = con
                    .prepareStatement("SELECT name,email,category,description,created_at FROM complaints");

            ResultSet r = ps.executeQuery();
            while (r.next()) {
                writer.println("<tr>");
                writer.println("<td>" + r.getString("name") + "</td>");
                writer.println("<td>" + r.getString("email") + "</td>");
                writer.println("<td>" + r.getString("category") + "</td>");
                writer.println("<td>" + r.getString("description") + "</td>");
                writer.println("<td>" + formatter.format(r.getTimestamp("created_at")) + "</td>");
                writer.println("</tr>");
            }

        } catch (Exception e) {
            writer.println("<script> alert(" + e.getMessage() + ");</script>");
            return;
        }

        writer.println("""
                            </table>
                        </center>
                    </body>
                </html>
                    """);
    }

}
