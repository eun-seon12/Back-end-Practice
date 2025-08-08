package com.projectnull.model.dao;

import com.projectnull.model.dto.UserDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.projectnull.common.JDBCTemplate.close;

public class UserDAO {

    private Properties prop = new Properties();

    public UserDAO (){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/projectnull/mapper/user-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Map<Integer, String>> selectAllUser(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<Map<Integer, String>> userList = null;

        String query = prop.getProperty("selectAllUserList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            userList = new ArrayList<>();

            while (rset.next()){
                Map<Integer, String> user = new HashMap<>();
                user.put(rset.getInt("USER_ID"), rset.getString("USER_NAME"));

                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }
        return userList;

    }

    public int selectOneUser(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        int oneUser = 0;

        String query = prop.getProperty("selectOneUser");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if (rset.next()){
                oneUser = rset.getInt("USER_ID");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }
        return oneUser;
    }

}
