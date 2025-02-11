package com.theanimegroup.horse_racing_client.DAO;

import com.theanimegroup.horse_racing_client.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    private static List<User> userList = new ArrayList<>();
    private static User currentUser = null;

    private UserDAO() {
        if(userList.isEmpty()) {
            userList.add(new User(1, "duclo", "123456", 100.0));
            userList.add(new User(2, "huylo", "123456", 100.0));
            userList.add(new User(3, "trinhlo", "123456", 100.0));
            userList.add(new User(4, "tamtamlo", "123456", 100.0));
            userList.add(new User(5, "huylelo", "123456", 100.0));
            userList.add(new User(6, "tienporn", "123456", 100.0));
        }
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User Login(String username, String password){
        for (int i=0; i<userList.size();i++){
            User _currentUser = userList.get(i);
            if(_currentUser.getUsername().equals(username) && _currentUser.getPassword().equals(password)){
                setCurrentUser(_currentUser);
                return currentUser;
            }
        }
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserDAO.currentUser = currentUser;
    }

    public static void updateCurrentUser(String newUsername, String newPassword, double newTotalCash) {
        if (currentUser != null) {
            currentUser.setUsername(newUsername);
            currentUser.setPassword(newPassword);
            currentUser.setTotalCash(newTotalCash);
        } else {
            System.out.println("Chưa có người dùng nào đăng nhập!");
        }
    }

}

