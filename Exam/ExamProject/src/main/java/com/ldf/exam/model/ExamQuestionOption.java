package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "examQuestionOptions")

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExamQuestionOption extends IdentityIntId{  /*implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
*/

    @ToString.Exclude //Avoid StackOverflow error
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @NotNull
    @JsonBackReference
    private ExamQuestion examQuestion;
    
    @Column
    @Size(max = 500)
    @NotNull
    private String answer;

    @Column
    @NotNull
    @JsonProperty("isTrue")
    private boolean isTrue;

    @Column
    private int position;

	
}
