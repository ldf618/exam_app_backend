package com.ldf.exam.model;

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
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="class")
@DiscriminatorValue("Users")

//Lombok
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class User extends IdentityIntId { /*implements Serializable{
	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;*/
        
        @Column
        @NotNull
        @Size(min = 2, max = 50)
	private String firstName;
        
        @Column
        @Size(max = 50)
	private String surname1;
        
        @Column
        @Size(max = 50)
	private String surname2;
        
        @Column
        @NotNull
        @Size(min = 9, max = 9)
	private String dni;
        
        @Column
        @NotNull
        @Size(min = 2, max = 50)
	private String userName;
        
        @Column
        @NotNull
        @Size(min = 2, max = 50)
	private String userPassword;
 
}
