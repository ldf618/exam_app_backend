package com.ldf.exam.model;

//javax.persistence

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

/**
 *
 * @author Lo
 */
@Entity
@Table(name = "Groups")
@NamedQueries({
    @NamedQuery(name = "group.findByClassroom", 
                query = "from Group g where g.classroom.id = :idClassroom"),
    @NamedQuery(name = "group.findByStudentAndClassroom", 
                query = "select g from Group g "
                        + "join g.students s "
                        + "where s.id=:idStudent and g.classroom.id=:idClassroom")
        /*
    @NamedQuery(name = "group.findByStudentAndClassroom", 
                query = "select g from Student s "
                        + "join s.groups g "
                        + "where s.id=:idStudent and g.classroom.id=:idClassroom")        */
})

//Lombok
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Group extends IdentityIntId {/*implements Serializable{
	
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;*/
        
        @Column
        @NotNull
        @Size(min = 2, max = 50)
	private String name;
        
        //Mapped by es el nombre de la lista en la clase Student
        @ManyToMany(/*mappedBy="groups",*/cascade = CascadeType.ALL) 
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        private List<Student> students;
        
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @ManyToOne(cascade = CascadeType.ALL)
	private Classroom classroom;
}
