package com.ldf.exam.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
@Table(name = "examQuestions")

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExamQuestion extends IdentityIntId{ //implements Serializable {

    public enum QuestionType {
        FREETEXT, OPTIONS, INDIVIDUAL_EVALUATION, GROUP_EVALUATION
    }
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
*/
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //@NotNull
    @ManyToOne
    private Exam exam;
  
    @Column
    @NotNull
    @Size(min = 2, max = 500)
    private String wording;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length = 25)
    private QuestionType type;

    @Column
    @NotNull
    private boolean isMultipleSelection;

    @Column
    private int position;
    
    //@ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (mappedBy = "examQuestion", fetch = FetchType.EAGER , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List <ExamQuestionOption> examQuestionOptions;
}