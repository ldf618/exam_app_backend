package com.ldf.exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "exams")

@NamedQueries({
    @NamedQuery(name = "exam.findByQuestionTypeAndCourse",
            query = "select distinct e from Exam e inner join e.examQuestions eq "
            + "where e.publicationDate is not null "
            + "and e.course.id=:idCourse and eq.type in (:questionType1,:questionType2)")
})

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Exam extends IdentityIntId {

    public enum ExamType {
        INDIVIDUAL, GROUP
    }
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
     */

    @Column
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Column
    @Size(max = 500)
    private String instructions;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length = 25)
    private ExamType type;

    @Column //(columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)    
    private LocalDate publicationDate;

    @Column //(columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)    
    private LocalDate creationDate;

    @Column //(columnDefinition = "DATE")
    private LocalDateTime changeDate;

    @Column //(columnDefinition = "DATE")
    private LocalDate deadline;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER/*, cascade = CascadeType.PERSIST*/)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //@JsonIgnore
    private Course course;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Consultant consultant;

    
    @OrderBy("position")
    @OneToMany(mappedBy = "exam", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    //@ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.SELECT) //Hibernate propietary but avoids duplication.Default FetchMode.JOIN duplicate rows 
    @JsonManagedReference
    private List<ExamQuestion> examQuestions;
    
    public void setExamQuestions(List<ExamQuestion> eq){
        examQuestions=eq;
        eq.forEach(q->{q.setExam(this);});
    }

    @PrePersist
    void createdAt() {
        this.creationDate =  LocalDate.now();
    }
}
