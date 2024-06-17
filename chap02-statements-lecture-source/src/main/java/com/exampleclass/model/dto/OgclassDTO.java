package com.exampleclass.model.dto;

public class OgclassDTO {
    private Integer studentNo;
    private String studentName;
    private String gender;
    private String githubId;
    private String email;
    private String mbti;
    private Integer subjectNo;

    public OgclassDTO(){}

    public OgclassDTO(Integer studentNo, String studentName, String gender, String githubId, String email, String mbti, Integer subjectNo) {
        this.studentNo = studentNo;
        this.studentName = studentName;
        this.gender = gender;
        this.githubId = githubId;
        this.email = email;
        this.mbti = mbti;
        this.subjectNo = subjectNo;
    }

    public Integer getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(Integer studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudent_name() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public Integer getSubjectNo() {
        return subjectNo;
    }

    public void setSubjectNo(Integer subjectNo) {
        this.subjectNo = subjectNo;
    }

    @Override
    public String toString() {
        return "OgclassDTO[" +
                "studentNo=" + studentNo +
                ", studentName='" + studentName + '\'' +
                ", gender='" + gender + '\'' +
                ", githubId='" + githubId + '\'' +
                ", email='" + email + '\'' +
                ", mbti='" + mbti + '\'' +
                ", subjectNo=" + subjectNo +
                ']';
    }
}
