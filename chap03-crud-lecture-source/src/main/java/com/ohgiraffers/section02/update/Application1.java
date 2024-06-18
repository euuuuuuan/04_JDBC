package com.ohgiraffers.section02.update;

import com.ohgiraffers.model.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        Connection con = getConnection();

        PreparedStatement pstmt = null;

        int result = 0;

        Properties prop = new Properties();

        Scanner sc = new Scanner(System.in);
        System.out.println("변경할 메뉴 번호를 입력하세요: ");
        int menuCode = sc.nextInt();

        System.out.println("변경할 메뉴 이름을 입력하세요: ");
        String menuName = sc.next();

        System.out.println("변경할 메뉴 가격을 입력하세요: ");
        int menuPrice = sc.nextInt();

        MenuDTO changedMenu = new MenuDTO();
        changedMenu.setMenuCode(menuCode);
        changedMenu.setMenuName(menuName);
        changedMenu.setMenuPrice(menuPrice);

        /*-----------------------------------------------*/

        // 쿼리 가져오기
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("updateMenu");

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, changedMenu.getMenuName());
            pstmt.setInt(2, changedMenu.getMenuPrice());
            pstmt.setInt(3, changedMenu.getMenuCode());

            result = pstmt.executeUpdate();

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }

        if (result > 0) {
            System.out.println("메뉴 수정 성공!!!");
        } else {
            System.out.println("메뉴 수정 실패!!!");
        }
//        System.out.println(result);
    }
}

// executeUpdate() 메서드:
// PreparedStatement 인터페이스에서 제공하는 메서드로,
// INSERT, UPDATE 또는 DELETE와 같은 DML(Data Manipulation Language) 쿼리를 실행할 때 사용됩니다.
// executeUpdate()는 데이터베이스에서 영향을 받는 행의 수를 반환합니다.
// 즉, 쿼리가 성공적으로 실행되고 데이터베이스에 변경이 일어났을 때 영향을 받는 행의 수를 반환합니다.