package com.ldf.exam.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "examQuestionOptionAnswers")

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExamQuestionOptionAnswer extends IdentityIntId {/* implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
     */

    @ManyToOne
    private ExamQuestionOption examQuestionOption;

    @ToString.Exclude //Avoid StackOverflow error
    @ManyToOne
    private ExamQuestionAnswer examQuestionAnswer;

    @Column
    @Size(max = 4000)
    @NotNull
    private String answer;

    @Column
    @NotNull
    private boolean isSelected;

    @OneToMany(mappedBy = "examQuestionOptionAnswer")
    private List <StudentOptionAnswerScore> Score;
}
