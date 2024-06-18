package com.exampleemp;

import com.exampleemp.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import static com.exampleemp.common.JDBCSetUp.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class App {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
//        Statement stmt = null;
        ResultSet rset = null;
        EmployeeDTO row = null;
        List<EmployeeDTO> empList = null;

        Properties prop = new Properties();

        try {
//            stmt = con.createStatement();
            prop.loadFromXML(new FileInputStream("src/main/java/com/exampleemp/config/connection-info.properties"));
            String querySeq = prop.getProperty("selectEmp");

            pstmt = con.prepareStatement(querySeq);
            rset = pstmt.executeQuery();
            empList = new ArrayList<>();

            while (rset.next()) {
                row = new EmployeeDTO();

                row.setEmpId(rset.getString("EMP_ID"));
                row.setEmpName(rset.getString("EMP_NAME"));
                row.setEmpNo(rset.getString("EMP_NO"));
                row.setEmail(rset.getString("PHONE"));
                row.setJobCode(rset.getString("JOB_CODE"));
                row.setSalLevel(rset.getString("SAL_LEVEL"));
                row.setSalary(rset.getFloat("SALARY"));
                row.setBonus(rset.getFloat("BONUS"));
                row.setManagerId(rset.getString("MANAGER_ID"));
                row.setHireDate(rset.getDate("HIRE_DATE"));
                row.setEntDate(rset.getDate("ENT_DATE"));
                row.setEntYn(rset.getString("ENT_YN"));

                empList.add(row);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);

            // empList가 null이 아닌 경우에만 반복문 실행
            if (empList != null) {
                for (EmployeeDTO emp : empList) {
                    System.out.println(emp);
                }
            } else {
                System.out.println("No employees found."); // 예외 발생 시 처리할 메시지 출력
            }

        }
    }
}
