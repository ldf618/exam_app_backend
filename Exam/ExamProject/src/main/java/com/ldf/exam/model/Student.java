package com.ldf.exam.model;

import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

//javax.persistence
@Entity
@DiscriminatorValue("Students")
//Lombok

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class Student extends User {
    
    /*
    @JoinTable(
        name = "rel_student_group",
        joinColumns = @JoinColumn(name = "FK_STUDENT", nullable = false),
        inverseJoinColumns = @JoinColumn(name="FK_GROUP", nullable = false)
    )*/
    @ManyToMany(mappedBy="students",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude    
    private List<Group> groups;
    
    /*
    @JoinTable(
        name = "rel_student_group",
        joinColumns = @JoinColumn(name = "FK_STUDENT", nullable = false),
        inverseJoinColumns = @JoinColumn(name="FK_GROUP", nullable = false)
    )*/
    @ManyToMany(mappedBy="students",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Classroom> classrooms;
    
    @OneToMany(mappedBy = "student")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List <StudentOptionAnswerScore> Score;

   /* @Builder(builderMethodName = "studentBuilder")
    public Student(int idUser, String name, String surname1, String surname2, String dni, String userName, String userPassword) {
        super(idUser, name, surname1, surname2, dni, userName, userPassword);
    }*/
}
