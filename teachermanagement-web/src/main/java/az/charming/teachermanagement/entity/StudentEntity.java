package az.charming.teachermanagement.entity;

import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="student")
@NamedQuery (query="select s from StudentEntity s where s.school.id=:id and s.age=:age", name= "nqfindBySchoolIdAndAge")
public class StudentEntity {

    @Id
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private BigDecimal scholarship;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    @JoinTable(name = "student_teacher",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<TeacherEntity> teacherList;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    public Integer getId() {
        return id;
    }

    public StudentEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public StudentEntity setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public StudentEntity setAge(Integer age) {
        this.age = age;
        return this;
    }

    public BigDecimal getScholarship() {
        return scholarship;
    }

    public StudentEntity setScholarship(BigDecimal scholarship) {
        this.scholarship = scholarship;
        return this;
    }

    public List<TeacherEntity> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherEntity> teacherList) {
        this.teacherList = teacherList;
    }

    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", scholarship=" + scholarship +
                '}';
    }
}
