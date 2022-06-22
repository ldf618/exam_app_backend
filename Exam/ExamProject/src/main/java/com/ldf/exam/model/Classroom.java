package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "classrooms")
@NamedQueries({
    @NamedQuery(name = "classroom.findByConsultantAndCourse",
            query = "from Classroom c where c.consultant.id=:idConsultant and c.course.id=:idCourse"),
    @NamedQuery(name = "classroom.findByStudentAndCourse",
            query = "select classroom from Classroom classroom "
            + "join classroom.students student "
            //                        + "join classroom.course course "
            + "where student.id=:idStudent and classroom.course.id=:idCourse")
})

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Classroom extends IdentityIntId {

    @Column
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Consultant consultant;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Course course;

    //Mapped by es el nombre de la lista en la clase Student
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    /*
            Classroom is the owner of the relationship (no mappedby anotation)
            when Classroom is persisted students will be persisted due to cascade ALL
            and table relation classroom_user will be inserted. 
            The other way round, if we made Student the owner of the relationship 
            when classroom is persisted table user_classroom will not be inserted  
     */
    @ManyToMany(/*mappedBy="classrooms",*/cascade = CascadeType.ALL)
    private List<Student> students;
}
