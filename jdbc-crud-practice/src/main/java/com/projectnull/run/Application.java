package com.projectnull.run;

import com.projectnull.common.JDBCTemplate;
import com.projectnull.model.dao.UserDAO;
import com.projectnull.model.dto.UserDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.projectnull.common.JDBCTemplate.close;
import static com.projectnull.common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        Connection con = null;

        UserDAO userDAO = new UserDAO();

        Scanner sc = new Scanner(System.in);

        try{
        con = JDBCTemplate.getConnection();

        while(true){
            System.out.println("=== 공연 예매 프로그램 회원 관리 ===");
            System.out.println("1. 전체 회원 조회");
            System.out.println("2. 새 회원 등록");
            System.out.println("3. 회원 정보 조회");
            System.out.println("4. 회원 정보 수정");
            System.out.println("5. 회원 삭제");
            System.out.println("9. 프로그램 종료");
            System.out.print("번호 선택 : ");
            int num = sc.nextInt();

            switch (num){
                case 1:
                    List<Map<String, String>> userList = userDAO.selectAllUser(con);

                    for (Map<String, String> user : userList){
                        System.out.println("user = " + user);
                    }
                    break;
                case 2:
                    System.out.print("등록할 회원의 아이디를 입력하세요 : ");
                    int userId = sc.nextInt();
                    System.out.print("등록할 회원의 이름을 입력하세요 : ");
                    sc.nextLine();
                    String userName = sc.nextLine();
                    System.out.print("등록할 회원의 비밀번호를 입력하세요 : ");
                    String userPwd = sc.nextLine();
                    System.out.print("등록할 회원의 나이를 입력하세요 : ");
                    int age = sc.nextInt();
                    System.out.print("등록할 회원의 전화번호를 입력하세요 : ");
                    sc.nextLine();
                    String phone = sc.nextLine();
                    System.out.print("등록할 회원의 성별을 입력하세요(F/M) : ");
                    String gender = sc.nextLine();

                    UserDTO newUser = new UserDTO(userId, userName, userPwd, age, phone, gender);

                    int insertResult = userDAO.insertUser(con, newUser);

                    if (insertResult >0){
                        System.out.println("등록 성공!");
                    } else {
                        System.out.println("등록 실패!");
                    }
                    break;
                case 3:
                    System.out.print("조회할 회원 아이디 : ");
                    int searchId = sc.nextInt();
                    UserDTO oneUser = userDAO.selectOneUser(con, searchId);
                    System.out.println("oneUser = " + oneUser);
                    break;
                case 4:
                    System.out.print("수정할 회원 아이디: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("새로운 비밀번호 : ");
                    String updatePwd = sc.nextLine();

                    System.out.print("새로운 전화번호 : ");
                    String updatePhone = sc.nextLine();

                    UserDTO updateUser = new UserDTO();
                    updateUser.setUserId(updateId);
                    updateUser.setUserPwd(updatePwd);
                    updateUser.setPhone(updatePhone);

                    int updateResult = userDAO.updateUser(con, updateUser);

                    if (updateResult>0){
                        System.out.println("수정 완료!");
                    } else{
                        System.out.println("수정 실패!");
                    }


                    break;
                case 5:
                    System.out.print("삭제할 회원 번호를 입력하세요 : ");
                    int deleteId = sc.nextInt();
                    int deleteResult = userDAO.deleteUser(con, deleteId);

                    if (deleteResult > 0) {
                        System.out.println("삭제 성공!");
                    } else {
                        System.out.println("삭제 실패!");
                    }
                    break;
                case 9:
                    System.out.println("프로그램을 종료합니다");
                    break;

                default:
                    System.out.println("번호를 다시 입력해주세요");
            }
            if (num==9){
                break;
            }
        }
        } finally {
            close(con);
        }
    }
}
