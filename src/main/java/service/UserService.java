package service;

import dao.UserDAO;
import model.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
}
