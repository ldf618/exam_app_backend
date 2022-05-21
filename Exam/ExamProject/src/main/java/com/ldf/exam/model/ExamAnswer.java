package com.ldf.exam.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "examAnswers")

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExamAnswer extends IdentityIntId{ //implements Serializable {

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    */

    private boolean finished;

    @Column 
    private LocalDate creationDate;

    @Column 
    private LocalDateTime changeDate;
   
    @NotNull
    @ManyToOne (fetch = FetchType.LAZY)
    private Student student;
    
    @NotNull
    @ManyToOne (fetch = FetchType.LAZY)
    private Exam exam;

   @OneToMany (mappedBy = "examAnswer", fetch = FetchType.LAZY , cascade = CascadeType.PERSIST, orphanRemoval = true)
   private List<ExamQuestionAnswer> examQuestionAnswers;
}
