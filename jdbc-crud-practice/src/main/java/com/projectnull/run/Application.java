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
            System.out.println("      1. 전체 회원 조회");
            System.out.println("      2. 새 회원 등록");
            System.out.println("      3. 회원 정보 조회"); // 조회할 회원이 존재하는지 확인
            System.out.println("      4. 회원 정보 수정"); // 수정할 회원이 존재하는지 확인
            System.out.println("      5. 회원 삭제");
            System.out.println("      9. 프로그램 종료");
            System.out.println();
            System.out.println("======================================");
            System.out.print("번호 선택 : ");
            int num = sc.nextInt();
            System.out.println();

            switch (num){
                case 1:
                    List<Map<String, String>> userList = userDAO.selectAllUser(con);

                    System.out.println("user code  |  user id  |  user name");
                    for (Map<String, String> user : userList){
                        System.out.println(user);
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.print("등록할 회원의 아이디를 입력하세요 : ");
                    sc.nextLine();
                    String userId = sc.nextLine();
                    System.out.print("등록할 회원의 이름을 입력하세요 : ");
                    String userName = sc.nextLine();
                    System.out.print("등록할 회원의 비밀번호를 입력하세요 : ");
                    String userPwd = sc.nextLine();
                    System.out.print("등록할 회원의 나이를 입력하세요 : ");
                    int age = sc.nextInt();
                    System.out.print("등록할 회원의 전화번호를 입력하세요 : ");
                    String phone = sc.nextLine();
                    System.out.print("등록할 회원의 성별을 입력하세요(F/M) : ");
                    String gender = sc.nextLine();

                    UserDTO newUser = new UserDTO(0, userId, userName, userPwd, age, phone, gender);

                    int insertResult = userDAO.insertUser(con, newUser);

                    if (insertResult >0){
                        System.out.println("등록 성공!");
                    } else {
                        System.out.println("등록 실패!");
                    }
                    break;
                case 3:

                    List<Map<String, String>> allUserList = userDAO.selectAllUser(con);

                    System.out.println("=== 회원 목록 ===");
                    for (Map<String, String> user : allUserList){
                        System.out.println(user);
                    }
                    System.out.println("================");

                    System.out.print("조회할 회원코드 : ");
                    int searchCode = sc.nextInt();

                    UserDTO oneUser = userDAO.selectOneUser(con, searchCode);

                    if (oneUser == null){
                        System.out.println("해당 회원이 존재하지 않습니다.");
                        break;
                    }

                    System.out.println("-------------------------------------------------------------------------------------");
                    System.out.println(oneUser);
                    System.out.println("-------------------------------------------------------------------------------------");
                    System.out.println();
                    break;
                case 4:
                    List<Map<String, String>> allUser = userDAO.selectAllUser(con);
                    System.out.println("=== 회원 목록 ===");
                    for (Map<String, String> user : allUser){
                        System.out.println(user);
                    }
                    System.out.println("================");

                    System.out.print("수정할 회원코드: ");
                    int updateCode = sc.nextInt();
                    sc.nextLine();

                    UserDTO updateUser = userDAO.selectOneUser(con, updateCode);

                    if (updateUser == null){
                        System.out.println("해당 회원이 존재하지 않습니다.");
                        break;
                    }
                    while (true){
                        System.out.println("****** 수정할 정보를 선택하세요 ******");
                        System.out.println("         1. 이름");
                        System.out.println("         2. 비밀번호");
                        System.out.println("         3. 전화번호");
                        System.out.println("         4. 성별");
                        System.out.println("         0. 수정 완료");
                        System.out.println("**********************************");
                        System.out.print("번호 선택 : ");
                        int choice = sc.nextInt();
                        sc.nextLine();

                        if (choice == 0) {
                            int updateResult = userDAO.updateUser(con, updateUser);
                            if (updateResult>0){
                                System.out.println("수정 완료!");
                                System.out.println();
                            } else{
                                System.out.println("수정 실패!");
                                System.out.println();
                            }
                            break;
                        }
                        switch (choice){
                            case 1:
                                System.out.print("새로운 이름 : ");
                                updateUser.setUserName(sc.nextLine());
                                System.out.println();
                                break;
                            case 2:
                                System.out.print("새로운 비밀번호 : ");
                                updateUser.setUserPwd(sc.nextLine());
                                System.out.println();
                                break;
                            case 3:
                                System.out.print("새로운 전화번호 : ");
                                updateUser.setPhone(sc.nextLine());
                                System.out.println();
                                break;
                            case 4:
                                System.out.print("새로운 성별 : ");
                                updateUser.setGender(sc.nextLine());
                                System.out.println();
                                break;
                            case 0:
                                System.out.println("수정 취소");
                                System.out.println();
                                break;
                            default:
                                System.out.println("번호를 다시 선택하세요");
                                System.out.println();
                                break;
                        }
                    }

                    break;
                case 5:
                    List<Map<String, String>> alluser = userDAO.selectAllUser(con);
                    System.out.println("=== 회원 목록 ===");
                    for (Map<String, String> user : alluser){
                        System.out.println(user);
                    }
                    System.out.println("================");

                    System.out.print("삭제할 회원 코드를 입력하세요 : ");
                    int deleteCode = sc.nextInt();
                    int deleteResult = userDAO.deleteUser(con, deleteCode);

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
