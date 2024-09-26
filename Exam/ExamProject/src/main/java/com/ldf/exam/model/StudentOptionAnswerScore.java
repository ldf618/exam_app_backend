package com.ldf.exam.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "StudentOptionAnswerScores")

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StudentOptionAnswerScore implements Serializable{
   
    @EmbeddedId
    private StudentOptionAnswerScorePK id;

    @ManyToOne
    @MapsId("studentId") //This is the name of attr in StudentOptionAnswerScorePK class
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("optionAnswerId")
    @JoinColumn(name = "examQuestionOptionAnswer_id")
    private ExamQuestionOptionAnswer examQuestionOptionAnswer;   
       
    @Column
    private int score;
}

