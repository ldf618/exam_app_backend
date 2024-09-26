package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "examQuestionAnswers")

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExamQuestionAnswer extends IdentityIntId {/*implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;*/

    @ToString.Exclude
    @NotNull
    @ManyToOne
    @JsonBackReference
    private ExamAnswer examAnswer;

    //@ToString.Exclude
    @NotNull
    @ManyToOne
    private ExamQuestion examQuestion;

    @Column
//    @NotNull
    @Size(max = 4000)
    private String answer;

    @JsonManagedReference
    @OneToMany(mappedBy = "examQuestionAnswer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamQuestionOptionAnswer> examQuestionOptionAnswer;
}
