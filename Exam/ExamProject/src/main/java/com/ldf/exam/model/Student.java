package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    
    @JsonInclude()
    @Transient
    private final String type="Student";
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //jwt. prevent the password text to be included in json rsponse
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_Student"));
    }
    
    /*
    @JoinTable(
        name = "rel_student_group",
        joinColumns = @JoinColumn(name = "FK_STUDENT", nullable = false),
        inverseJoinColumns = @JoinColumn(name="FK_GROUP", nullable = false)
    )*/
    @ManyToMany(mappedBy="students",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude  
    @JsonIgnore
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
    @JsonIgnore
    private List<Classroom> classrooms;
    
    @OneToMany(mappedBy = "student")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List <StudentOptionAnswerScore> Score;

   /* @Builder(builderMethodName = "studentBuilder")
    public Student(int idUser, String name, String surname1, String surname2, String dni, String userName, String userPassword) {
        super(idUser, name, surname1, surname2, dni, userName, userPassword);
    }*/
    
    public void copyFromUser(User user) {
        //(user.getId(), user.getFirstname(), user.getSurname1(), user.getSurname2(), user.getDni(), user.getUsername(), user.getPassword(), user.getPhoto());
        BeanUtils.copyProperties(user, this);
    }
    
    
}
