package com.projectnull.run;

import com.projectnull.common.JDBCTemplate;
import com.projectnull.model.dao.ReservationDAO;
import com.projectnull.model.dao.UserDAO;
import com.projectnull.model.dto.ReservationDTO;
import com.projectnull.model.dto.UserDTO;

import java.security.PublicKey;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.projectnull.common.JDBCTemplate.close;
import static com.projectnull.common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        Connection con = null;
        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        ReservationDAO reservationDAO = new ReservationDAO();


        try {
            con = JDBCTemplate.getConnection();


            while (true) {
                System.out.println("============================================");
                System.out.println("         공연 예매 및 회원 관리 프로그램  ");
                System.out.println();
                System.out.println("            1. 예매 관리");
                System.out.println("            2. 회원 관리");
                System.out.println("            9. 프로그램 종료");
                System.out.println("============================================");
                System.out.print("메뉴 선택 : ");
                int mainChoice = sc.nextInt();
                sc.nextLine();
                System.out.println();

                if (mainChoice == 9) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }
                switch (mainChoice) {

                    case 1:
                        while (true) {
                            System.out.println("============================================");
                            System.out.println("        🎫 공연 예매 관리 🎫       ");
                            System.out.println();
                            System.out.println("          1. 전체 예매 조회");
                            System.out.println("          2. 새 예매 등록");
                            System.out.println("          3. 예매 정보 조회");
                            System.out.println("          4. 예매 정보 수정");
                            System.out.println("          5. 예매 삭제");
                            System.out.println("          9. 종료");
                            System.out.println("============================================");
                            System.out.print("번호 선택 : ");
                            int num = sc.nextInt();
                            System.out.println();

                            if (num == 9) break;

                            switch (num) {
                                case 1:
                                    List<Map<String, Integer>> reservationList = reservationDAO.selectAllReservation(con);
                                    printReservationList(reservationList);

                                    break;
                                case 2:
                                    System.out.print("등록할 회원 코드를 입력하세요 :  ");
                                    int userCode = sc.nextInt();
                                    System.out.print("등록할 공연 코드를 입력하세요 : ");
                                    int showCode = sc.nextInt();
                                    System.out.print("등록할 좌석 코드를 입력하세요 : ");
                                    int seatCode = sc.nextInt();


                                    ReservationDTO newReservation = new ReservationDTO(0, userCode, showCode, seatCode);

                                    int insertResult = reservationDAO.insertReservation(con, newReservation);

                                    if (insertResult > 0) {
                                        System.out.println("등록 성공!");
                                    } else {
                                        System.out.println("등록 실패!");
                                    }
                                    break;
                                case 3:
                                    System.out.println("< 예매 목록 >");
                                    List<Map<String, Integer>> allReservationList = reservationDAO.selectAllReservation(con);
                                    printReservationList(allReservationList);

                                    System.out.print("조회할 예매 코드 : ");
                                    int searchCode = sc.nextInt();

                                    ReservationDTO oneResv = reservationDAO.selectOneResv(con, searchCode);

                                    if (oneResv == null) {
                                        System.out.println("해당 예매는 존재하지 않습니다.");
                                        break;
                                    }

                                    System.out.println();
                                    System.out.println(oneResv);
                                    System.out.println();
                                    break;
                                case 4:
                                    System.out.println("< 예매 목록 >");
                                    List<Map<String, Integer>> allReservationList2 = reservationDAO.selectAllReservation(con);
                                    printReservationList(allReservationList2);

                                    System.out.print("수정할 예매 코드: ");
                                    int updateCode = sc.nextInt();
                                    sc.nextLine();

                                    ReservationDTO updateResv = reservationDAO.selectOneResv(con, updateCode);

                                    if (updateResv == null) {
                                        System.out.println("해당 예매는 존재하지 않습니다.");
                                        break;
                                    }

                                    while (true) {
                                        System.out.println("*****************************");
                                        System.out.println("*      수정할 정보 선택        *");
                                        System.out.println("*        1. 공연 코드         *");
                                        System.out.println("*        2. 좌석 코드         *");
                                        System.out.println("*        0. 수정 완료         *");
                                        System.out.println("*****************************");
                                        System.out.print("번호 선택 : ");
                                        int choice = sc.nextInt();
                                        sc.nextLine();

                                        if (choice == 0) {
                                            int updateResult = reservationDAO.updateReservation(con, updateResv);
                                            if (updateResult > 0) {
                                                System.out.println("수정 완료!");
                                                System.out.println();
                                            } else {
                                                System.out.println("수정 실패!");
                                                System.out.println();
                                            }
                                            break;
                                        }
                                        switch (choice) {
                                            case 1:
                                                System.out.print("새로운 공연코드 : ");
                                                updateResv.setShowCode(sc.nextInt());
                                                System.out.println();
                                                break;
                                            case 2:
                                                System.out.print("새로운 좌석코드 : ");
                                                updateResv.setSeatCode(sc.nextInt());
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
                                    System.out.println("< 예매 목록 >");
                                    List<Map<String, Integer>> allReservationList3 = reservationDAO.selectAllReservation(con);
                                    printReservationList(allReservationList3);

                                    System.out.print("삭제할 예매 코드를 입력하세요 : ");
                                    int deleteCode = sc.nextInt();
                                    int deleteResult = reservationDAO.deleteReservation(con, deleteCode);

                                    if (deleteResult > 0) {
                                        System.out.println("삭제 성공!");
                                    } else {
                                        System.out.println();
                                        System.out.println("존재하지 않는 예매입니다.");
                                        System.out.println();
                                    }
                                    break;

                                default:
                                    System.out.println("번호를 다시 입력해주세요");

                            }

                        }
                        break;



                    case 2:
                        while (true) {
                            System.out.println("============================================");
                            System.out.println("            👩 회원 관리 메뉴 👨          ");
                            System.out.println();
                            System.out.println("            1. 전체 회원 조회");
                            System.out.println("            2. 새 회원 등록");
                            System.out.println("            3. 회원 정보 조회");
                            System.out.println("            4. 회원 정보 수정");
                            System.out.println("            5. 회원 삭제");
                            System.out.println("            9. 종료");
                            System.out.println("============================================");
                            System.out.print("번호 선택 : ");
                            int num = sc.nextInt();
                            System.out.println();

                            if (num == 9)
                                break;

                            switch (num) {
                                case 1:
                                    List<Map<String, String>> userList = userDAO.selectAllUser(con);
                                    printUserList(userList);
                                    break;
                                case 2:
                                    sc.nextLine();
                                    System.out.print("등록할 회원의 아이디를 입력하세요 : ");
                                    String userId = sc.nextLine();
                                    System.out.print("등록할 회원의 이름을 입력하세요 : ");
                                    String userName = sc.nextLine();
                                    System.out.print("등록할 회원의 비밀번호를 입력하세요 : ");
                                    String userPwd = sc.nextLine();
                                    System.out.print("등록할 회원의 나이를 입력하세요 : ");
                                    int age = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("등록할 회원의 전화번호를 입력하세요 : ");
                                    String phone = sc.nextLine();
                                    System.out.print("등록할 회원의 성별을 입력하세요(F/M) : ");
                                    String gender = sc.nextLine();

                                    UserDTO newUser = new UserDTO(0, userId, userName, userPwd, age, phone, gender);

                                    int insertResult = userDAO.insertUser(con, newUser);

                                    if (insertResult > 0) {
                                        System.out.println("등록 성공!");
                                    } else {
                                        System.out.println("등록 실패!");
                                    }
                                    break;


                                case 3:
                                    System.out.println("< 회원 목록 >");
                                    List<Map<String, String>> allUserList = userDAO.selectAllUser(con);
                                    printUserList(allUserList);

                                    System.out.print("조회할 회원코드 : ");
                                    int searchCode = sc.nextInt();

                                    UserDTO oneUser = userDAO.selectOneUser(con, searchCode);

                                    if (oneUser == null) {
                                        System.out.println("해당 회원은 존재하지 않습니다.");
                                        break;
                                    }

                                    System.out.println();
                                    System.out.println(oneUser);
                                    System.out.println();
                                    break;
                                case 4:
                                    System.out.println("< 회원 목록 >");
                                    List<Map<String, String>> allUserList2 = userDAO.selectAllUser(con);
                                    printUserList(allUserList2);

                                    System.out.print("수정할 회원코드: ");
                                    int updateCode = sc.nextInt();
                                    sc.nextLine();

                                    UserDTO updateUser = userDAO.selectOneUser(con, updateCode);

                                    if (updateUser == null) {
                                        System.out.println("해당 회원은 존재하지 않습니다.");
                                        break;
                                    }
                                    while (true) {
                                        System.out.println("*****************************");
                                        System.out.println("*      수정할 정보 선택        *");
                                        System.out.println("*        1. 이름             *");
                                        System.out.println("*        2. 비밀번호          *");
                                        System.out.println("*        3. 전화번호          *");
                                        System.out.println("*        4. 성별             *");
                                        System.out.println("*        0. 수정 완료         *");
                                        System.out.println("*****************************");
                                        System.out.print("번호 선택 : ");
                                        int choice = sc.nextInt();
                                        sc.nextLine();

                                        if (choice == 0) {
                                            int updateResult = userDAO.updateUser(con, updateUser);
                                            if (updateResult > 0) {
                                                System.out.println("수정 완료!");
                                                System.out.println();
                                            } else {
                                                System.out.println("수정 실패!");
                                                System.out.println();
                                            }
                                            break;
                                        }
                                        switch (choice) {
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
                                    System.out.println("< 회원 목록 >");
                                    List<Map<String, String>> allUserList3 = userDAO.selectAllUser(con);
                                    printUserList(allUserList3);

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
                                default:
                                    System.out.println("번호를 다시 입력해주세요");
                            }
                        }
                        break;

                    default:
                        System.out.println("번호를 다시 입력해주세요");
                }
            }
        } finally {
            close(con);
        }
    }

    public static void printReservationList(List<Map<String, Integer>> reservationList) {

        System.out.println("------------------------------------------------");
        System.out.println(" 예매 코드  |  회원 코드  |  공연 코드  |  좌석 코드  ");
        System.out.println("------------------------------------------------");
        for (Map<String, Integer> reservation : reservationList) {
            System.out.printf("%-16d %-10d %-10d %-10d%n",
                    reservation.get("reservation_code"),
                    reservation.get("user_code"),
                    reservation.get("show_code"),
                    reservation.get("seat_code"));
        }

        System.out.println();
    }

    public static void printUserList(List<Map<String, String>> userList) {
        System.out.println("--------------------------------");
        System.out.println(" 회원 코드 | 회원 아이디 | 회원 이름 ");
        System.out.println("--------------------------------");
        for (Map<String, String> user : userList) {
            System.out.printf("%-10d %-10s %-10s%n",
                    Integer.parseInt(user.get("userCode")),
                    user.get("userId"),
                    user.get("userName"));
        }
        System.out.println();
    }
}
