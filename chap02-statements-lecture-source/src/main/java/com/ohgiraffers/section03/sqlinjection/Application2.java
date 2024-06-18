package com.ohgiraffers.section03.sqlinjection;

import java.sql.*;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        // Employee 회원 ID와 이름을 입력받고 두개가 일치하는 회원이 있는지 확인하는 기능

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("ID와 이름 두개가 일치하는 employee가 있는지 확인하는 기능입니다.");
        System.out.println("ID를 입력하세요: ");
        String empId = sc.nextLine();
        System.out.println("회원의 이름을 입력하세요: ");
        String empName = sc.nextLine();

        String query = "select * from employee where emp_id = ? and emp_name = ?";
        System.out.println(query);

        // ID를 입력하세요:
        //12435234
        //회원의 이름을 입력하세요:
        //' or 1=1 and emp_id = '204
        //select * from employee where emp_id = '12435234'and emp_name = '' or 1=1 and emp_id = '204'

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, empId);
            pstmt.setString(2, empName);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                System.out.println(rset.getString("emp_name") + "님 환영합니다.");

            } else {
                System.out.println(" 회원 정보가 없습니다. ");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }
}
// Statement
//Statement 인터페이스는 가장 기본적인 형태의 SQL 문을 실행할 때 사용됩니다. 주로 정적인 SQL 쿼리를 실행할 때 사용합니다. Statement를 사용할 때는 매번 SQL 쿼리를 실행할 때마다 새로 컴파일하고 데이터베이스로 전송합니다.
//
//주요 특징:
//
//정적인 SQL 쿼리를 실행할 때 사용됩니다.
//SQL 쿼리를 실행하기 전에 이미 컴파일된 상태가 아니므로, 매번 실행할 때마다 데이터베이스에 새로 전송하고 컴파일합니다.
//일반적으로 Statement 객체를 사용하여 SQL Injection 공격에 취약할 수 있습니다.
//사용 예시:
//
//Statement stmt = con.createStatement();
//ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
//PreparedStatement
//PreparedStatement 인터페이스는 동적인 SQL 쿼리를 실행할 때 사용됩니다. 즉, 실행 시점에 매개 변수를 사용하여 SQL 쿼리를 구성하고 실행할 수 있습니다. 이는 성능 향상과 코드의 보안성을 높이는 데 도움이 됩니다.
//
//주요 특징:
//
//동적인 SQL 쿼리를 실행할 때 사용됩니다. 매개 변수(placeholder)를 사용하여 쿼리를 준비하고 실행할 수 있습니다.
//쿼리가 미리 컴파일되어 데이터베이스에 한 번만 전송됩니다. 같은 쿼리를 여러 번 실행할 경우 성능이 향상됩니다.
//보안상의 이점이 있어 SQL Injection 공격을 방지할 수 있습니다.
//사용 예시:
//
//String sql = "SELECT * FROM employee WHERE emp_id = ?";
//PreparedStatement pstmt = con.prepareStatement(sql);
//pstmt.setInt(1, 123);
//ResultSet rs = pstmt.executeQuery();
//주요 차이점 요약
//동적 vs 정적: PreparedStatement는 동적인 SQL 쿼리를 실행할 수 있고, Statement는 정적인 SQL 쿼리를 실행합니다.
//컴파일과 전송: PreparedStatement는 쿼리가 미리 컴파일되어 데이터베이스에 전송되며, 반복적인 실행에서 성능이 향상됩니다. Statement는 매번 실행할 때마다 쿼리를 컴파일하고 전송합니다.
//보안: PreparedStatement는 매개 변수를 사용하여 SQL Injection 공격을 방지할 수 있는 반면, Statement는 쿼리 문자열을 직접 작성하기 때문에 보안에 취약할 수 있습니다.
//일반적으로는 가능한 경우 PreparedStatement를 사용하여 코드의 성능과 보안성을 높이는 것이 좋습니다.

// 1. Statement
//특징:
//정적인 SQL 쿼리를 실행할 때 사용됩니다.
//쿼리 문자열이 매번 데이터베이스로 전송되고 실행될 때마다 컴파일됩니다.
//사용자 입력을 직접 포함할 경우 SQL Injection 공격에 취약할 수 있습니다.
//ResultSet에서 데이터를 읽을 때는 커서를 이용하여 한 번에 하나의 행만 읽을 수 있습니다.
//사용 예시:

//Statement stmt = con.createStatement();
//ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
//// 또는
//int rowsUpdated = stmt.executeUpdate("UPDATE employee SET salary = 50000 WHERE emp_id = 123");


//2. PreparedStatement
//특징:
//
//동적인 SQL 쿼리를 실행할 때 사용됩니다.
//쿼리를 한 번만 데이터베이스로 전송하여 컴파일된 후, 매개 변수(placeholder)를 이용하여 여러 번 실행할 수 있습니다.
//보안성이 뛰어나고, SQL Injection 공격을 방지할 수 있습니다.
//실행 시 성능이 더 우수하며, 데이터베이스에서 성능 최적화를 할 수 있는 장점이 있습니다.
//ResultSet에서 데이터를 읽을 때도 커서를 이용하여 순차적으로 읽을 수 있습니다.
//사용 예시:

//String sql = "SELECT * FROM employee WHERE emp_id = ?";
//PreparedStatement pstmt = con.prepareStatement(sql);
//pstmt.setInt(1, 123);
//ResultSet rs = pstmt.executeQuery();


//3. createStatement()
//특징:
//
//Statement 객체를 생성하여 정적인 SQL 쿼리를 실행할 때 사용됩니다.
//쿼리를 실행할 때마다 데이터베이스로 새로 전송하고 컴파일합니다.
//PreparedStatement와 달리 매개 변수를 사용할 수 없으며, 동적 쿼리 작성이 어렵습니다.
//ResultSet에서 데이터를 읽을 때도 커서를 이용하여 순차적으로 읽을 수 있습니다.
//사용 예시:

//Statement stmt = con.createStatement();
//ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE emp_id = 123");
//비교 요약
//유형: Statement는 정적 SQL 쿼리 실행, PreparedStatement는 동적 SQL 쿼리 실행에 적합합니다.
//보안: PreparedStatement는 SQL Injection 공격을 예방할 수 있습니다.
//성능: PreparedStatement는 쿼리를 한 번만 컴파일하여 반복 실행 시 성능이 우수합니다.
//사용성: PreparedStatement는 매개 변수를 이용하여 쿼리를 동적으로 구성할 수 있습니다.
//따라서 데이터베이스 접근 코드를 작성할 때는 사용하는 SQL 쿼리의 유형과 상황에 맞게 Statement와 PreparedStatement를 적절히 선택하여 사용하는 것이 중요합니다.



// 정적 SQL 쿼리 (Static SQL Query)
//정적 SQL 쿼리는 실행 시 데이터베이스에 보내는 쿼리의 내용이 고정되어 있는 경우를 말합니다. 즉, 쿼리 문자열이 컴파일되고, 실행 시점에서 변경되지 않는 쿼리를 의미합니다. 주로 다음과 같은 특징을 가집니다:
//
//특징:
//
//쿼리 문자열은 고정되어 있어 실행 시점에서 변경되지 않습니다.
//정적 쿼리는 주로 데이터베이스 구조나 정해진 데이터를 조회할 때 사용됩니다.
//컴파일 시점에 쿼리가 검사되고 최적화되므로 실행 속도가 빠를 수 있습니다.
//사용 예시:
//
//SELECT * FROM employees WHERE emp_id = 123;
//INSERT INTO employees (emp_id, emp_name, emp_salary) VALUES (123, 'John Doe', 50000);
//장점:
//
//실행 계획 최적화를 할 수 있어 성능이 우수할 수 있습니다.
//보통 구조가 단순하여 코드 작성이 간단할 수 있습니다.
//단점:
//
//동적 데이터에 적용하기 어렵습니다.
//반복적인 코드 작성이 필요할 수 있습니다.
//동적 SQL 쿼리 (Dynamic SQL Query)
//동적 SQL 쿼리는 실행 시점에 쿼리의 일부 또는 전체가 변경될 수 있는 쿼리를 의미합니다. 즉, 사용자 입력이나 프로그램 로직에 따라 쿼리가 동적으로 생성되거나 변경될 수 있습니다. 주로 다음과 같은 특징을 가집니다:
//
//특징:
//
//실행 시점에 데이터베이스에 전달되는 쿼리가 변할 수 있습니다.
//사용자 입력을 포함하여 다양한 상황에 맞게 쿼리를 구성할 수 있습니다.
//보통 프로그램 로직에 따라 쿼리를 동적으로 구성합니다.
//사용 예시:
//
//String empName = "John Doe";
//String sql = "SELECT * FROM employees WHERE emp_name = '" + empName + "'";
//장점:
//
//다양한 조건에 맞게 쿼리를 유연하게 구성할 수 있습니다.
//동적 데이터에 적용하기 좋습니다.
//단점:
//
//SQL Injection 공격에 취약할 수 있습니다. (사용자 입력을 직접 쿼리에 추가할 경우)
//실행 시점에서 쿼리 최적화가 어려울 수 있습니다.
//비교 요약
//정적 SQL 쿼리는 실행 시점에 변경되지 않고, 고정된 쿼리를 사용합니다. 코드는 간단하고, 성능이 좋을 수 있습니다.
//동적 SQL 쿼리는 실행 시점에 쿼리가 변경될 수 있으며, 사용자 입력에 따라 쿼리를 유연하게 구성할 수 있습니다. 그러나 SQL Injection 공격에 취약할 수 있습니다.
//애플리케이션의 요구사항과 데이터베이스 접근 패턴에 따라 정적 SQL 쿼리와 동적 SQL 쿼리 중 적합한 방법을 선택하여 사용하는 것이 중요합니다.