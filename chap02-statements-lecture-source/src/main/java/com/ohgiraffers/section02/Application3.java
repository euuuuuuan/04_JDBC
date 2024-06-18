package com.ohgiraffers.section02;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        /*
         * 위치홀더 : ?
         *
         * java 쿼리문은 파싱을 통해 컴파일 돼서 Database에 쿼리를 수행해 결과값을 가져온다.
         * Statement -> SQL문 실행시 매번 쿼리를 새롭게 인식해서 컴파일 진행
         * PreparedStatement -> 조건값을 ? 로 두고 다른 쿼리를 미리 컴파일 해둔뒤
         *                       쿼리는 변경하지 않고, 바인딩되는 변수만 바꿔서 SQL문이 실행된다.
         * */

        // Scanner를 사용한 PreparedStatement
        Connection con = getConnection();

        PreparedStatement pstmt = null;

        ResultSet rset = null;

        // Scanner 사번 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 사번을 입력해주세요: ");
        String empId = sc.nextLine();

        String query = "select * from employee where emp_id = ?";

        EmployeeDTO selectedEmp = null;

        try {
//            pstmt = con.prepareStatement("select emp_id, emp_name from employee " +
//                    "where emp_id = ?");
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, empId);

            rset = pstmt.executeQuery();

            if (rset.next()) {

                selectedEmp = new EmployeeDTO();

                selectedEmp.setEmpId(rset.getString("EMP_ID"));
                selectedEmp.setEmpName(rset.getString("EMP_NAME"));
                selectedEmp.setEmpNo(rset.getString("EMP_NO"));
                selectedEmp.setEmail(rset.getString("EMAIL"));
                selectedEmp.setPhone(rset.getString("PHONE"));
                selectedEmp.setDeptCode(rset.getString("DEPT_CODE"));
                selectedEmp.setJobCode(rset.getString("JOB_CODE"));
                selectedEmp.setSalLevel(rset.getString("SAL_LEVEL"));
                selectedEmp.setSalary(rset.getDouble("SALARY"));
                selectedEmp.setBonus(rset.getDouble("BONUS"));
                selectedEmp.setManagerId(rset.getString("MANAGER_ID"));
                selectedEmp.setHireDate(rset.getDate("HIRE_DATE"));
                selectedEmp.setEntDate(rset.getDate("ENT_DATE"));
                selectedEmp.setEntYn(rset.getString("ENT_YN"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }

        System.out.println("selectedEmp = " + selectedEmp);
    }
}

// ?는 JDBC(Java Database Connectivity)에서 사용되는 placeholder이며, SQL 쿼리의 매개 변수를 나타냅니다.
// 주로 PreparedStatement를 사용할 때 등장합니다. PreparedStatement는 SQL 쿼리를 미리 컴파일하여 데이터베이스에
// 여러 번 전송할 수 있도록 도와주며, 이때 ?를 사용하여 동적으로 값을 설정할 수 있습니다.
//
//여기서 "select * from employee where emp_id = ?" 쿼리에서 ?는 "emp_id"라는 매개 변수를 나타냅니다.
// 이 쿼리를 실행할 때, 프로그램은 PreparedStatement 객체를 생성하고, setInt() 또는 setString() 등의
// 메서드를 사용하여 ? 자리에 실제 값(정수 또는 문자열 등)을 설정한 후 실행합니다.
// 이는 SQL 쿼리의 재사용성을 높이고 SQL 인젝션 공격을 방지하는 데 도움이 됩니다.
//
//간단한 예제를 보겠습니다:
//
//String query = "select * from employee where emp_id = ?";
//int empId = 123; // 예를 들어 emp_id가 123일 경우
//
//PreparedStatement pstmt = connection.prepareStatement(query);
//pstmt.setInt(1, empId);
//
//ResultSet rs = pstmt.executeQuery();
//
//// 여기서 적절히 결과를 처리하면 됩니다.
//위 예제에서 setInt(1, empId) 메서드는 첫 번째 매개 변수(1)에 empId 값을 설정합니다.
// 따라서 실행될 쿼리는 "select * from employee where emp_id = 123"이 됩니다.

// ?는 JDBC에서 사용하는 특별한 기호로, SQL 쿼리의 매개 변수를 의미합니다. 이를 통해 쿼리를 실행할 때마다 다른 값을 전달할 수 있습니다. 예를 들어,
//
//java
//코드 복사
//String query = "select * from employee where emp_id = ?";
//위 쿼리에서 ? 자리에는 나중에 설정할 실제 값이 들어갑니다.
// 예를 들어, emp_id가 123일 때 이 쿼리를 실행하고 싶다면:
//
//int empId = 123;
//
//PreparedStatement pstmt = connection.prepareStatement(query);
//pstmt.setInt(1, empId);
//
//ResultSet rs = pstmt.executeQuery();
//여기서 setInt(1, empId)는 첫 번째 ? 자리에 empId 값을 설정하라는 의미입니다.
// 그래서 최종적으로 실행되는 SQL은 select * from employee where emp_id = 123이 됩니다.