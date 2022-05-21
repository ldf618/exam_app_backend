package com.ldf.exam.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode
@ToString
public abstract class IdentityIntId implements Serializable{
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
}
