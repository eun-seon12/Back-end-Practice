package com.projectnull.run;

import com.projectnull.model.dao.UserDAO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.projectnull.common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        Connection con = getConnection();

        UserDAO registDAO = new UserDAO();

        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("===공연 예매 프로그램 회원 관리===");
            System.out.println("1. 전체 회원 조회");
            System.out.println("2. 새 회원 등록");
            System.out.println("3. 회원 정보 조회");
            System.out.println("4. 회원 정보 수정");
            System.out.println("5. 회원 삭제");
            System.out.print("번호 선택 : ");
            int num = sc.nextInt();

//            switch (num){
//                case 1:
//
//            }
        }
        /* 1. 새 회원 등록 */


//        System.out.print("등록할 회원의 아이디를 입력하세요 : ");
//        int userId = sc.nextInt();
//        System.out.print("등록할 회원의 이름을 입력하세요 : ");
//        sc.nextLine();
//        String userName = sc.nextLine();
//        System.out.print("등록할 회원의 비밀번호를 입력하세요 : ");
//        String userPwd = sc.nextLine();
//        System.out.print("등록할 회원의 나이를 입력하세요 : ");
//        int age = sc.nextInt();
//        System.out.print("등록할 회원의 전화번호를 입력하세요 : ");
//        sc.nextLine();
//        String phone = sc.nextLine();
//        System.out.print("등록할 회원의 성별을 입력하세요(F/M) : ");
//        String gender = sc.nextLine();



        /* 2. 전체 회원 조회 */

        List<Map<Integer, String>> userList = registDAO.selectAllUser(con);

        for (Map<Integer, String> user : userList){
            System.out.println("user = " + user);
        }


        /* 3. 1번 회원 상세 조회 */

        int oneUser = registDAO.selectOneUser(con);

        System.out.println("oneUser = " + oneUser);

        /* 4. 1번 회원 수정 */




        /* 5. 1번 회원 삭제 */
    }
}
