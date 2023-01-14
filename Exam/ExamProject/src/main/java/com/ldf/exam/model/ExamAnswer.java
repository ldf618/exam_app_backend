package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @ManyToOne (fetch = FetchType.EAGER)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Student student;
    
    @NotNull
    @ManyToOne (fetch = FetchType.LAZY)
    private Exam exam;

    
   @EqualsAndHashCode.Exclude
   @Fetch(FetchMode.SELECT) //Hibernate propietary but avoids duplication.Default FetchMode.JOIN duplicate rows 
   @JsonManagedReference
   @OneToMany (mappedBy = "examAnswer", fetch = FetchType.EAGER , cascade = CascadeType.ALL, orphanRemoval = true)
   private List<ExamQuestionAnswer> examQuestionAnswers;
   
   public void setExamQuestionAnswers(List<ExamQuestionAnswer> eqa){
        examQuestionAnswers=eqa;
        eqa.forEach(q->{q.setExamAnswer(this);System.out.println(q);});
    }
}
