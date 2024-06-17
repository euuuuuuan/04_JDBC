package com.ohgiraffers.section02.template;

import java.sql.Connection;

import static com.ohgiraffers.section02.template.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        // 데이터베이스와 연결하는 연결 객체
        Connection con = getConnection();

        // 연결객체 생성여부 확인 (객체의 주소값이 반환되는 것을 확인)
        System.out.println("con = " + con);
    }
}
