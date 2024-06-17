package com.ohgiraffers.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {

        // 1. Connection 객체 생성
        Connection con = getConnection();

        // 2. Statement 생성
        Statement stmt = null;

        // 3. ResultSet 생성
        ResultSet rset = null;

        try {

            // 4. 연결 객체의 createStatement()를 이용한 Statement 객체 생성
            stmt = con.createStatement();

            // 스캐너로 emp_id를 입력받아 조회하기
            Scanner sc = new Scanner(System.in);
            System.out.println("조회하려는 사번을 입력하세요: ");
            String empId = sc.nextLine();

            String query = "select emp_id, emp_name from employee where emp_id = '" + empId + "'";

            // 5. executeQuery()로 쿼리문을 실행하고 결과를 ResultSet에 반환 받기
            rset = stmt.executeQuery(query);

            // 6. 쿼리문의 결과를 column 이름을 이용해서 사용
            if (rset.next()) {
                System.out.println(rset.getString("EMP_ID")
                        + ", " + rset.getString("EMP_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 7. 사용한 자원 반납
            close(rset);
            close(stmt);
            close(con);
        }
    }
}

// 34번 행의 이 코드에서 따옴표가 이중으로 사용된 이유는 SQL 쿼리 문자열에 포함된 emp_id 변수를 SQL 쿼리에 적절하게 포함하기 위함입니다.
//
//여기서 empId는 문자열 변수로 가정하겠습니다.
// SQL 쿼리에서 문자열 값을 포함할 때는 일반적으로 따옴표(')로 감싸야 합니다.
// 예를 들어, emp_id가 "101"이라면 SQL 쿼리는 다음과 같이 됩니다:
//
//sql
//select * from employee where emp_id = '101'
//따라서 Java 코드에서는 이 문자열을 생성할 때 변수 empId의 값을 포함시켜야 합니다. Java에서 문자열을 결합할 때는 문자열 연결 연산자 +를 사용합니다. 따라서 코드에서는 다음과 같이 작성됩니다:
//
//java
//String empId = "101";
//String query = "select * from employee where emp_id = '" + empId + "'";
//여기서 "'" + empId + "'" 부분은 empId 변수의 값인 "101"을 포함한 SQL 쿼리 문자열을 생성합니다. 따라서 최종적으로 query 변수에는 다음과 같은 문자열이 할당됩니다:
//
//sql
//select * from employee where emp_id = '101'
//이렇게 하면 SQL 쿼리가 올바르게 형성되며, empId의 값에 따라 동적으로 쿼리가 생성됩니다.