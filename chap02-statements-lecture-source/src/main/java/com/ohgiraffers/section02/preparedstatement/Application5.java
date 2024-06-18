package com.ohgiraffers.section02.preparedstatement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application5 {
    public static void main(String[] args) {
        // 연결객체 만들기
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        // 조회할 employee의 이름의 성을 받아서 찾기
        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 이름의 성을 입력하세요: ");
        String empName = sc.nextLine();

        // concat(?, '%') -> ? 로 시작하는 것
//        String query = "select * from employee where emp_name like concat(?, '%')";

        EmployeeDTO row = null;
        List<EmployeeDTO> empList = null;
        // List는 Java 컬렉션 프레임워크의 인터페이스로, 순서가 있는 요소들의 집합을 표현
        // EmployeeDTO는 데이터베이스에서 조회한 각각의 직원 정보를 저장하기 위한 클래스일 것입니다.
        // empList: 이는 변수의 이름으로, 리스트 객체를 참조할 때 사용됩니다.
        // = null;: 이는 변수를 초기화하는 부분으로, empList를 아직 생성하지 않았음을 나타냅니다.
        // 즉, 아직 객체가 메모리에 할당되지 않았으며, null 값을 가지고 있습니다.

        // 이렇게 선언된 empList는 나중에 데이터베이스에서 조회한 결과를 담기 위해 사용됩니다.
        // 조회한 각 직원 정보를 EmployeeDTO 객체로 생성하고, 이 객체들을 empList에 추가하여 저장할 수 있습니다.


        Properties prop = new Properties();

        try {

            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/section02/preparedstatement/employee-query.xml"));
            String query = prop.getProperty("selectEmpByFamilyName");

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, empName);

            rset = pstmt.executeQuery();

            empList = new ArrayList<>();

            while (rset.next()) {

                row = new EmployeeDTO();

                row.setEmpId(rset.getString("EMP_ID"));
                row.setEmpName(rset.getString("EMP_NAME"));
                row.setEmpNo(rset.getString("EMP_NO"));
                row.setEmail(rset.getString("EMAIL"));
                row.setPhone(rset.getString("PHONE"));
                row.setDeptCode(rset.getString("DEPT_CODE"));
                row.setJobCode(rset.getString("JOB_CODE"));
                row.setSalLevel(rset.getString("SAL_LEVEL"));
                row.setSalary(rset.getDouble("SALARY"));
                row.setBonus(rset.getDouble("BONUS"));
                row.setManagerId(rset.getString("MANAGER_ID"));
                row.setHireDate(rset.getDate("HIRE_DATE"));
                row.setEntDate(rset.getDate("ENT_DATE"));
                row.setEntYn(rset.getString("ENT_YN"));

                empList.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        for (EmployeeDTO emp : empList) {
            System.out.println(emp);
        }
    }
}
