package com.ohgiraffers.section01.insert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    // C : insert
    public static void main(String[] args) {

        Connection con = getConnection();
        System.out.println(con); // con주소 잘받아오는지 확인

        PreparedStatement pstmt = null;

        /*
         * insert, update, delete의 성공한 행의 갯수를 반환해준다. -> 정수 형태
         * 0은 아무런 변화가 없을 때
         * */
        int result = 0;

        // 쿼리문이 저장된 XML 파일을 읽어올 Properties 객체
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = prop.getProperty("insertMenu");
        System.out.println("query = " + query);
    }
}
