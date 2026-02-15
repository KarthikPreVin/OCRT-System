import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.sql.*;

public class PostComplaint extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        writer.println(
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Complaint logged</title>
                            <style>
                                input,textarea{
                                    display: block;
                                    margin-bottom: 10px;
                                }
                            </style>
                        </head>
                        <body>
                            <span>To login as admin click </span><a href="login">here</a>
                            <h2>POST COMPLAINT</h2>
                            <form action="post-complaint" method="POST" id="my-form">
                                <label for="name">Name</label>
                                <input type="text" placeholder="enter name" id="name" name="name" required>
                                <label for="email">Email</label>
                                <input type="email" placeholder="enter email" id="email" name="email" required>
                                <label for="category">Complaint Category</label>
                                <input type="text" placeholder="enter category" id="category" name="category" required>
                                <label for="description">Description</label>
                                <textarea name="description" placeholder="enter description" id="description" cols="50" rows="4" required></textarea>
                                <button type="submit">SUBMIT</button>
                            </form>

                            <script>
                                name = document.getElementById("name");
                                email = document.getElementById("email");
                                category = document.getElementById("category");
                                description = document.getElementById("description");

                                myform = document.getElementById("my-form");

                                myform.addEventListener("submit", onSubmit);

                                function onSubmit(event){
                                    if (!/^[A-Za-z0-9\s]*$/.test(name.value)){
                                        event.preventDefault();
                                        alert("Name should contain only alphanumeric characters");
                                    }
                                }
                            </script>
                        </body>
                        </html>
                                """);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        String p1 = request.getParameter("name");
        String p2 = request.getParameter("email");
        String p3 = request.getParameter("category");
        String p4 = request.getParameter("description");

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
                    .prepareStatement("INSERT INTO complaints (name,email,category,description) values(?,?,?,?)");
            ps.setString(1, p1);
            ps.setString(2, p2);
            ps.setString(3, p3);
            ps.setString(4, p4);

            int rows = ps.executeUpdate();
            System.out.println(rows + " records inserted");

            ps.close();
            con.close();

        }

        catch (Exception e) {
            writer.println("<script> alert(" + e.getMessage() + ");history.back();</script>");
            return;
        }

        writer.println("""
                                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Complaint logged</title>
                </head>
                <body>
                    <h2>COMPLAINT HAS BEEN LOGGED SUCCESSFULLY</h2>
                    <form action="post-complaint" method="GET">
                        <button type="submit">GO BACK</button>
                    </form>
                </body>
                </html>
                                """);
    }
}