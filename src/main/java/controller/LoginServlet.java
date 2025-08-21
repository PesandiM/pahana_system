package controller;

import dto.LoginRequestDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.LoginService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final LoginService loginService = new LoginService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        LoginRequestDTO loginRequest = new LoginRequestDTO(username, password);
        User user = loginService.validateLogin(loginRequest);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            if ("admin".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect("adminDashboard.jsp");
            } else if ("staff".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect("staffDashboard.jsp");
            } else {
                resp.sendRedirect("unauthorized.jsp");
            }
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
