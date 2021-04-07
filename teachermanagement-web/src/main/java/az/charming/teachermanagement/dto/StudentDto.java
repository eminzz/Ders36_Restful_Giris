package az.charming.teachermanagement.dto;

import az.charming.teachermanagement.entity.StudentEntity;
import az.charming.teachermanagement.entity.TeacherEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StudentDto {

    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private BigDecimal scholarship;

    private SchoolDto school;
    private List<TeacherDto> teacherList;

    public String getFullname(){
        return name+" "+surname;
    }

    public static StudentDto instance(StudentEntity st){
        StudentDto studentDto = new StudentDto();
        studentDto.setId(st.getId());
        studentDto.setAge(st.getAge());
        studentDto.setName(st.getName());
        studentDto.setSurname(st.getSurname());
        studentDto.setScholarship(st.getScholarship());

        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setId(st.getSchool().getId());
        schoolDto.setName(st.getSchool().getName());
        studentDto.setSchool(schoolDto);

        List<TeacherDto> teacherDtos= new ArrayList<>();
        for(TeacherEntity teacher: st.getTeacherList()){
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setId(teacher.getId());
            teacherDto.setName(teacher.getName());
            teacherDtos.add(teacherDto);
        }
        studentDto.setTeacherList(teacherDtos);
        return studentDto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getScholarship() {
        return scholarship;
    }

    public void setScholarship(BigDecimal scholarship) {
        this.scholarship = scholarship;
    }

    public SchoolDto getSchool() {
        return school;
    }

    public void setSchool(SchoolDto school) {
        this.school = school;
    }

    public List<TeacherDto> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherDto> teacherList) {
        this.teacherList = teacherList;
    }
}
