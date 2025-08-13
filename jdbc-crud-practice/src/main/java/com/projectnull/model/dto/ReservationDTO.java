package com.projectnull.model.dto;

public class ReservationDTO {

    private int reservationCode;
    private int userCode;
    private int showCode;
    private int seatCode;

    public ReservationDTO() {}

    public ReservationDTO(int reservationCode, int userCode, int showCode, int seatCode) {
        this.reservationCode = reservationCode;
        this.userCode = userCode;
        this.showCode = showCode;
        this.seatCode = seatCode;
    }

    public int getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getShowCode() {
        return showCode;
    }

    public void setShowCode(int showCode) {
        this.showCode = showCode;
    }

    public int getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(int seatCode) {
        this.seatCode = seatCode;
    }

    @Override
    public String toString() {
        return
                "예매코드 '" + reservationCode + "'" +
                " => 회원코드 : " + userCode +
                ", 공연코드 : " + showCode +
                ", 좌석코드 : " + seatCode;
    }
}
