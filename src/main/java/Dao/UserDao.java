package Dao;

import Domain.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import utills.Dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UserDao {
    PreparedStatement pst = null;
    Connection conn = Dbconnect.getConn();
    Gson gson = new Gson();


    //ShowAllUser
    public String getAllUser() {

        List<User> data = new ArrayList<>();
        String json;
        String sql = "SELECT * FROM user";

        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User us = new User();
                us.setId(rs.getInt("id"));
                us.setName(rs.getString("name"));
                us.setPassword(rs.getString("password"));
                us.setEmail(rs.getString("email"));
                data.add(us);
            }
            rs.close();
            pst.close();
            json = gson.toJson(data);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    //FindById
    public String findUserById(int id) {

        String json;
        String sql = "SELECT * FROM user WHERE  id = " + id;
        User us = null;

        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            us = new User();
            while (rs.next()) {
                us.setId(rs.getInt("id"));
                us.setName(rs.getString("name"));
                us.setPassword(rs.getString("password"));
                us.setEmail(rs.getString("email"));

            }

            if (us.getId() == 0) {
                json = "not found";
            } else {
                json = gson.toJson(us);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    //CreateUser
    public boolean Createuser(String Payload) {
        if (Payload == null) return false;

        User user = (User) gson.fromJson(Payload, User.class);

        String sql = "INSERT INTO user (name, password, email) VALUES (?,?,?)";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.executeUpdate();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            throw new Error(e);
        }
        return false;
    }


    //UpdateUser
    public String UpdateUser(String payload, int id) {
        String json;
        User user = (User) gson.fromJson(payload, User.class);

        String sql = "UPDATE user SET name=?,password=?,email=? WHERE  id =?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setInt(4, id);

            pst.executeUpdate();

            json = findUserById(id);
            pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return json;
    }


}
