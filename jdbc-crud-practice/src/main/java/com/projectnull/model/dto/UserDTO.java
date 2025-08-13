package com.projectnull.model.dto;

public class UserDTO {

    private int userCode;
    private String userId;
    private String userName;
    private String userPwd;
    private int age;
    private String phone;
    private String gender;

    public UserDTO() {}

    public UserDTO(int userCode, String userId, String userName, String userPwd, int age, String phone, String gender) {
        this.userCode = userCode;
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
    }
    public int getUserCode(){return userCode;}

    public void setUserCode(int userCode){this.userCode=userCode;}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return " 회원 코드 = " + userCode +
                ", 아이디 = '" + userId + '\'' +
                ", 이름 = '" + userName + '\'' +
                ", 비밀번호 = '" + userPwd + '\'' +
                ", 나이 = " + age +
                ", 전화번호 = '" + phone + '\'' +
                ", 성별 = '" + gender + '\''
                ;
    }
}
