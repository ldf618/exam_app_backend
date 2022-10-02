package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
/*@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id",
  scope = Consultant.class
)*/
public class Consultant extends User {

    /*
    @Builder(builderMethodName = "consultantBuilder")
    public Consultant(int idUser, String firstName, String surname1, String surname2, String dni, String userName, String userPassword) {
        super(idUser, firstName, surname1, surname2, dni, userName, userPassword);
    }
*/
    
    @JsonInclude()
    @Transient
    private final String type="Consultant";
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_Consultant"));
    }
 /*   
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany (mappedBy = "consultant")
    @JsonIgnore
    private List<Classroom> classrooms;
 */   
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany (mappedBy = "consultant")
    //@JsonManagedReference
    @JsonIgnore
    private List<Exam> exams;
   
    
}
