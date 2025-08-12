package com.projectnull.model.dao;

import com.projectnull.model.dto.UserDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
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

    public int insertUser(Connection con, UserDTO user) {

        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertUser");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserPwd());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getGender());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }


    public List<Map<String, String>> selectAllUser(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<Map<String, String>> userList = null;

        String query = prop.getProperty("selectAllUserList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            userList = new ArrayList<>();

            while (rset.next()){
                Map<String, String> user = new HashMap<>();
                user.put("user Id ", String.valueOf(rset.getInt("USER_ID")));
                user.put("user Name ", rset.getString("USER_NAME"));


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

    public UserDTO selectOneUser(Connection con, int userId) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        UserDTO oneUser = null;


        String query = prop.getProperty("selectOneUser");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, userId);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                oneUser = new UserDTO();
                oneUser.setUserId(rset.getInt("USER_ID"));
                oneUser.setUserName(rset.getString("USER_NAME"));
                oneUser.setUserPwd(rset.getString("USER_PWD"));
                oneUser.setAge(rset.getInt("AGE"));
                oneUser.setPhone(rset.getString("PHONE"));
                oneUser.setGender(rset.getString("GENDER"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }
        return oneUser;
    }

    public int updateUser(Connection con, UserDTO user) {

        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("updateUser");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserPwd());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4,user.getGender());
            pstmt.setInt(5, user.getUserId());

            result = pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }


    public int deleteUser(Connection con, int userId) {

        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("deleteUser");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, userId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }

}