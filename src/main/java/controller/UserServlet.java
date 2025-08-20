package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list"; // default
        }

        switch (action) {
            case "new":
                request.getRequestDispatcher("users/addUser.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("user_id"));
                User user = userService.getAllUsers().stream()
                        .filter(u -> u.getUserId() == editId)
                        .findFirst().orElse(null);
                request.setAttribute("user", user);
                request.getRequestDispatcher("users/addUser.jsp").forward(request, response);
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("user_id"));
                userService.deleteUser(deleteId);
                response.sendRedirect("users");
                break;
            case "list":
                List<User> users = userService.getAllUsers();
                request.setAttribute("userList", users);

                String status = request.getParameter("status");
                if (status != null) {
                    if ("success".equals(status)) {
                        request.setAttribute("message", "User added successfully!");
                    } else if ("updated".equals(status)) {
                        request.setAttribute("message", "User updated successfully!");
                    } else if ("deleted".equals(status)) {
                        request.setAttribute("message", "User deleted successfully!");
                    }
                }

                request.getRequestDispatcher("users/viewUsers.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("user_id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        if (id == null || id.isEmpty()) {
            // add new
            userService.addUser(user);
            response.sendRedirect("users?action=list&status=success");
        } else {
            // update existing
            user.setUserId(Integer.parseInt(id));
            userService.updateUser(user);
            response.sendRedirect("users?action=list&status=updated");
        }
    }
}
