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
            System.out.println("====== 공연 예매 프로그램 회원 관리 ======");
            System.out.println();
            System.out.println("1. 전체 회원 조회");
            System.out.println("2. 새 회원 등록");
            System.out.println("3. 회원 정보 조회"); // 조회할 회원이 존재하는지 확인
            System.out.println("4. 회원 정보 수정"); // 수정할 회원이 존재하는지 확인
            System.out.println("5. 회원 삭제");
            System.out.println("9. 프로그램 종료");
            System.out.println();
            System.out.print("번호 선택 : ");
            int num = sc.nextInt();

            switch (num){
                case 1:
                    List<Map<String, String>> userList = userDAO.selectAllUser(con);

                    for (Map<String, String> user : userList){
                        System.out.println("user = " + user);
                    }
                    System.out.println();
                    break;
                case 2:
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

                    UserDTO newUser = new UserDTO(0, userName, userPwd, age, phone, gender);

                    int insertResult = userDAO.insertUser(con, newUser);

                    if (insertResult >0){
                        System.out.println("등록 성공!");
                    } else {
                        System.out.println("등록 실패!");
                    }
                    break;
                case 3:
//                    List<Map<String, String>> userList = userDAO.selectAllUser(con);
//                    for (Map<String, String> user : userList){
//                        System.out.println("user = " + user);
//                    }
//                    System.out.println();
                    // 조회할 회원 아이디 입력 전에 현재 전체 회원 리스트를 보여주기

                    System.out.print("조회할 회원 아이디 : ");
                    int searchId = sc.nextInt();

                    UserDTO oneUser = userDAO.selectOneUser(con, searchId);
                    System.out.println("-------------------------------------------------------------------------------------");
                    System.out.println(oneUser);
                    System.out.println("-------------------------------------------------------------------------------------");
                    System.out.println();
                    break;
                case 4:
                    System.out.print("수정할 회원 아이디: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    UserDTO updateUser = userDAO.selectOneUser(con, updateId);
//                    UserDTO updateUser = new UserDTO();
//                    updateUser.setUserId(updateId);

                    while (true){
                        System.out.println("====== 수정할 정보를 선택하세요 ======");
                        System.out.println("1. 이름");
                        System.out.println("2. 비밀번호");
                        System.out.println("3. 전화번호");
                        System.out.println("4. 성별");
                        System.out.println("0. 수정 완료");
                        System.out.print("번호 선택 : ");
                        int choice = sc.nextInt();
                        sc.nextLine();

                        if (choice == 0) {
                            int updateResult = userDAO.updateUser(con, updateUser);
                            if (updateResult>0){
                                System.out.println("수정 완료!");
                            } else{
                                System.out.println("수정 실패!");
                            }
                            break;
                        }
                        switch (choice){
                            case 1:
                                System.out.print("새로운 이름 : ");
                                updateUser.setUserName(sc.nextLine());
                                break;
                            case 2:
                                System.out.print("새로운 비밀번호 : ");
                                updateUser.setUserPwd(sc.nextLine());
                                break;
                            case 3:
                                System.out.print("새로운 전화번호 : ");
                                updateUser.setPhone(sc.nextLine());
                                break;
                            case 4:
                                System.out.print("새로운 성별 : ");
                                updateUser.setGender(sc.nextLine());
                                break;
                            case 0:
                                System.out.println("수정 취소");
                                break;
                            default:
                                System.out.println("번호를 다시 선택하세요");
                                break;
                        }
                    }

                    break;
                case 5:
                    System.out.print("삭제할 회원 번호를 입력하세요 : ");
                    int deleteId = sc.nextInt();
                    int deleteResult = userDAO.deleteUser(con, deleteId);

                    if (deleteResult > 0) {
                        System.out.println("삭제 성공!");
                    } else {
                        System.out.println();
                        System.out.println("존재하지 않는 회원입니다.");
                        System.out.println();
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
