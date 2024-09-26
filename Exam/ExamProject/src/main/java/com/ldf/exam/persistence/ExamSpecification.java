/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Course;
import com.ldf.exam.model.Exam;
import com.ldf.exam.model.Exam_;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author ldiez
 */
public final class ExamSpecification {
    
    public static Specification<Exam> getSpecificationForNull() {
           return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();        
    }
    
    public static Specification<Exam> nameLike(String name) {
        if (name == null)
            return getSpecificationForNull();
        else 
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(criteriaBuilder.upper(root.get(Exam_.NAME)), name.toUpperCase() + "%");        
    }

    public static Specification<Exam> typeEquals(Exam.ExamType type) {
        if (type == null) 
            return getSpecificationForNull();
        else 
            return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(Exam_.TYPE), type);
    }

    public static Specification<Exam> isPublished(Boolean published) {
        if (published == null) {
            return getSpecificationForNull();
        } else if (published) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.isNotNull(root.get(Exam_.PUBLICATION_DATE));
        } else {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.isNull(root.get(Exam_.PUBLICATION_DATE));
        }
    }


    public static Specification<Exam> createdAfter(LocalDate date) {
        if (date == null) {
            return getSpecificationForNull();
        } else {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get(Exam_.CREATION_DATE), date);
        }
    }

    public static Specification<Exam> createdBefore(LocalDate date) {
        if (date == null) {
            return getSpecificationForNull();
        } else {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThanOrEqualTo(root.get(Exam_.CREATION_DATE), date);
        }
    }
    
    public static Specification<Exam> courseEquals(Course course) {
        if (course == null) {
            return getSpecificationForNull();
        } else {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get(Exam_.COURSE), course);
        }
    }

    public static Specification<Exam> createSpecification(String name, Exam.ExamType type, Boolean published, LocalDate startDate, LocalDate endDate, Course course) {

        Specification<Exam> specification = Specification
                .where(nameLike(name))
                .and(isPublished(published))
                .and(typeEquals(type))
                .and(createdAfter(startDate))
                .and(createdBefore(endDate))
                .and(courseEquals(course));

        return specification;
    }
}
