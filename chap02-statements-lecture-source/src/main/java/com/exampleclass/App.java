package com.exampleclass;

import com.exampleclass.model.dto.OgclassDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.exampleclass.common.JDBCFrame.close;
import static com.exampleclass.common.JDBCFrame.getConnection;

public class App {
    public static void main(String[] args) {

        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        List<OgclassDTO> classList = null;
        OgclassDTO row = null;

        try {
            stmt = con.createStatement();
            String querySeq = "select * from class";

            rset = stmt.executeQuery(querySeq);
            classList = new ArrayList<>();

            while (rset.next()) {
                row = new OgclassDTO();

                row.setStudentNo(rset.getInt("student_no"));
                row.setStudentName(rset.getString("student_name"));
                row.setGender(rset.getString("gender"));
                row.setGithubId(rset.getString("github_id"));
                row.setEmail(rset.getString("email"));
                row.setMbti(rset.getString("mbti"));
                row.setSubjectNo(rset.getInt("subject_no"));

                classList.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
            close(con);

            for (OgclassDTO og : classList) {
                System.out.println(og);
            }
        }
    }
}
