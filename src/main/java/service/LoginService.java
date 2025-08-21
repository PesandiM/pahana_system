package service;

import dao.UserDAO;
import dto.LoginRequestDTO;
import model.User;

public class LoginService {
    private UserDAO userDAO = new UserDAO();

    public User validateLogin(LoginRequestDTO request) {
        return userDAO.findByUsernameAndPassword(request.getUsername(), request.getPassword());
    }
}
