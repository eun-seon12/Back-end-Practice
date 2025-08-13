package com.projectnull.model.dao;

import com.projectnull.model.dto.ReservationDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.projectnull.common.JDBCTemplate.close;

public class ReservationDAO {

    private Properties prop = new Properties();

    public ReservationDAO (){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/projectnull/mapper/reservation-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Map<String, Integer>> selectAllReservation(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<Map<String, Integer>> reservationList = new ArrayList<>();

        String query = prop.getProperty("selectAllResvList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);


            while (rset.next()){
                Map<String,Integer> reservation = new HashMap<>();

                reservation.put("reservation_code", rset.getInt("reservation_code"));
                reservation.put("user_code", rset.getInt("user_code"));
                reservation.put("show_code", rset.getInt("show_code"));
                reservation.put("seat_code", rset.getInt("seat_code"));

                reservationList.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(stmt);
        }
        return reservationList;

    }

    public int insertReservation(Connection con, ReservationDTO newReservation) {

        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertReservation");

        try {
            pstmt=con.prepareStatement(query);
            pstmt.setInt(1, newReservation.getUserCode());
            pstmt.setInt(2, newReservation.getShowCode());
            pstmt.setInt(3, newReservation.getSeatCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }

    public ReservationDTO selectOneResv(Connection con, int searchCode) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        ReservationDTO oneResv = null;

        String query = prop.getProperty("selectOneReservation");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, searchCode);

            rset = pstmt.executeQuery();

            if (rset.next()){
                oneResv = new ReservationDTO();
                oneResv.setReservationCode(rset.getInt("RESERVATION_CODE"));
                oneResv.setUserCode(rset.getInt("USER_CODE"));
                oneResv.setSeatCode(rset.getInt("SEAT_CODE"));
                oneResv.setShowCode(rset.getInt("SHOW_CODE"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }
        return oneResv;
    }

    public int updateReservation(Connection con, ReservationDTO updateResv) {

        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("updateReservation");

        try {
            pstmt=con.prepareStatement(query);
            pstmt.setInt(1, updateResv.getShowCode());
            pstmt.setInt(2, updateResv.getSeatCode());
            pstmt.setInt(3, updateResv.getReservationCode() );

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }

    public int deleteReservation(Connection con, int deleteCode) {

        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("deleteReservation");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, deleteCode);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }


}
