package com.ldf.exam.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
@Table(name="degrees")

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Degree extends IdentityIntId /*implements Serializable*/{

    /*
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
      */  
        @NotNull
        @Column
        @Size(min=2, max=50)
	private String name;
        
        //cascade = CascadeType.PERSIST, orphanRemoval = true si eliminamos de la lista se elimina de la BD
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @OneToMany (mappedBy = "degree", fetch = FetchType.LAZY , cascade = CascadeType.PERSIST, orphanRemoval = true)
        private List<Course> courses;
	
}
