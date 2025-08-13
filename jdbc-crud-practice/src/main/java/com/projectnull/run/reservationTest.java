package com.projectnull.run;

import com.projectnull.common.JDBCTemplate;
import com.projectnull.model.dao.ReservationDAO;
import com.projectnull.model.dao.UserDAO;
import com.projectnull.model.dto.ReservationDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.projectnull.common.JDBCTemplate.close;

public class reservationTest {

    public static void main(String[] args) {


        Connection con = null;

        ReservationDAO reservationDAO = new ReservationDAO();

        Scanner sc = new Scanner(System.in);

        try {
            con = JDBCTemplate.getConnection();

            while(true){
                System.out.println("============================================");
                System.out.println("        🎫 공연 예매 프로그램 예매 관리 🎫       ");
                System.out.println();
                System.out.println("          1. 전체 예매 조회");
                System.out.println("          2. 새 예매 등록");
                System.out.println("          3. 예매 정보 조회");
                System.out.println("          4. 예매 정보 수정");
                System.out.println("          5. 예매 삭제");
                System.out.println("          9. 프로그램 종료");
                System.out.println("============================================");
                System.out.print("번호 선택 : ");
                int num = sc.nextInt();
                System.out.println();

                switch (num) {
                    case 1:
                        System.out.println("------------------------------------------------");
                        System.out.println(" 예매 코드  |  회원 코드  |  공연 코드  |  좌석 코드  ");
                        System.out.println("------------------------------------------------");
                        List<Map<String, Integer>> reservationList = reservationDAO.selectAllReservation(con);
                        System.out.println();
                        break;
                    case 2:
                        System.out.print("등록할 회원 코드를 입력하세요 :  ");
                        int userCode = sc.nextInt();
                        System.out.print("등록할 좌석 코드를 입력하세요 : ");
                        int seatCode = sc.nextInt();
                        System.out.print("등록할 공연 코드를 입력하세요 : ");
                        int showCode = sc.nextInt();

                        ReservationDTO newReservation = new ReservationDTO(0, userCode, showCode, seatCode);

                        int insertResult = reservationDAO.insertReservation(con, newReservation);

                        if (insertResult > 0 ){
                            System.out.println("등록 성공!");
                        } else {
                            System.out.println("등록 실패!");
                        }
                        break;
                    case 3:
                        System.out.println("< 예매 목록 >");
                        System.out.println("------------------------------------------------");
                        System.out.println(" 예매 코드  |  회원 코드  |  공연 코드  |  좌석 코드  ");
                        System.out.println("------------------------------------------------");
                        List<Map<String, Integer>> allReservationList = reservationDAO.selectAllReservation(con);
                        System.out.println();

                        System.out.print("조회할 예매 코드 : ");
                        int searchCode = sc.nextInt();

                        ReservationDTO oneResv = reservationDAO.selectOneResv(con, searchCode);

                        if (oneResv == null){
                            System.out.println("해당 예매가 존재하지 않습니다.");
                            break;
                        }

                        System.out.println();
                        System.out.println(oneResv);
                        System.out.println();
                        break;
                    case 4:
                        System.out.println("< 예매 목록 >");
                        System.out.println("------------------------------------------------");
                        System.out.println(" 예매 코드  |  회원 코드  |  공연 코드  |  좌석 코드  ");
                        System.out.println("------------------------------------------------");
                        List<Map<String, Integer>> allReservationList2 = reservationDAO.selectAllReservation(con);
                        System.out.println();

                        System.out.print("수정할 예매 코드: ");
                        int updateCode = sc.nextInt();
                        sc.nextLine();

                        ReservationDTO updateResv = reservationDAO.selectOneResv(con, updateCode);

                        if (updateResv == null){
                            System.out.println("해당 예매가 존재하지 않습니다.");
                            break;
                        }

                        while (true){
                            System.out.println("수정할 정보를 선택하세요");
                            System.out.println("1. 공연코드");
                            System.out.println("2. 좌석코드");
                            System.out.println("0. 수정 완료");
                            System.out.print("번호 선택 : ");
                            int choice = sc.nextInt();
                            sc.nextLine();

                            if (choice ==0 ){
                                int updateResult = reservationDAO.updateReservation(con, updateResv);
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
                        } break;


                    case 5:
                        System.out.println("< 예매 목록 >");
                        System.out.println("------------------------------------------------");
                        System.out.println(" 예매 코드  |  회원 코드  |  공연 코드  |  좌석 코드  ");
                        System.out.println("------------------------------------------------");
                        List<Map<String, Integer>> allReservationList3 = reservationDAO.selectAllReservation(con);
                        System.out.println();

                        System.out.println("삭제할 예약 코드를 입력하세요 : ");
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
