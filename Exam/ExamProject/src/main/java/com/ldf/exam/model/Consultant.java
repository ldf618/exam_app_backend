package com.ldf.exam.model;

import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

//javax.persistence
@Entity

@DiscriminatorValue("Consultants")

//Lombok
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Consultant extends User {

    /*
    @Builder(builderMethodName = "consultantBuilder")
    public Consultant(int idUser, String firstName, String surname1, String surname2, String dni, String userName, String userPassword) {
        super(idUser, firstName, surname1, surname2, dni, userName, userPassword);
    }
*/
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany (mappedBy = "consultant")
    private List<Classroom> classrooms;
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany (mappedBy = "consultant")
    private List<Exam> exams;
    
    
}
