/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Course_;
import com.ldf.exam.model.Exam;
import com.ldf.exam.model.ExamQuestion;
import com.ldf.exam.model.ExamQuestion_;
import com.ldf.exam.model.Exam_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lo
 */
@Repository
public class ExamRepoCustomImpl implements ExamRepoCustom {
    
    
    @Autowired
    EntityManager em;
       
    // see https://www.baeldung.com/spring-data-criteria-queries 5. Using JPA Specifications
    @Override
    public List<Exam> findByQuestionTypeAndCourseCriteria(Integer idCourse, ExamQuestion.QuestionType questionType1, ExamQuestion.QuestionType questionType2){
      return null;   
      /*
        CriteriaBuilder builder = em.getCriteriaBuilder();        
        CriteriaQuery<Exam> cq = builder.createQuery(Exam.class);   
        Root<Exam> root = cq.from(Exam.class);
        cq.select(root);        
        //Join<Exam, ExamQuestion> examQuestion= root.join(Exam_.examQuestions, JoinType.LEFT);
        ListJoin<Exam, ExamQuestion>examQuestion= root.joinList(Exam_.EXAM_QUESTIONS,JoinType.LEFT);
        //cq.multiselect(root, examQuestion);
        cq.select(root);

        List<Predicate> restrictions = new ArrayList<>();
        restrictions.add(builder.isNotNull(root.get(Exam_.PUBLICATION_DATE)));
        restrictions.add(builder.equal(root.get(Exam_.course).get(Course_.id),idCourse));
        restrictions.add(builder.in(examQuestion.get(ExamQuestion_.TYPE)).value(questionType1).value(questionType2));
        cq.where(restrictions.toArray(new Predicate[restrictions.size()]));

        TypedQuery<Exam> query = em.createQuery(cq);
        List<Exam> result =  query.getResultList();
        return result;*/
     }
    
}
