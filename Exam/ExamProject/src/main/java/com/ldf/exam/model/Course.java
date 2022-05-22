package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

//javax.persistence
@Entity
@Table(name = "Courses")
//Lombok
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Course extends IdentityIntId{/*implements Serializable {

    	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;*/
        
        @Column
        @NotNull
        @Size(min = 2, max = 50)
	private String name;
                
        @Column
	private double credits;
        
        @ManyToOne(cascade ={CascadeType.PERSIST})
        @JsonBackReference
        private Degree degree;

}
