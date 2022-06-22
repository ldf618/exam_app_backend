package com.ldf.exam;

import com.ldf.exam.persistence.ClassroomRepo;
import com.ldf.exam.persistence.CourseRepo;
import com.ldf.exam.persistence.DegreeRepo;
import com.ldf.exam.persistence.GroupRepo;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExamApplicationTests {
    
    @Autowired
    private DegreeRepo degreeRepo;
    @Autowired
    private CourseRepo courseRepo;    
    @Autowired
    private ClassroomRepo classroomRepo;        
    @Autowired
    private GroupRepo groupRepo;            
    

	@Test
	void contextLoads() {
            //System.out.println(degreeRepo.findDegreesByStudentId(1));
            //System.out.println(courseRepo.findCoursesByStudentIdAndDegreeId(1,1));
            //System.out.println(classroomRepo.findClassroomByStudentIdAndCourseId(1,1));
            //System.out.println(Arrays.toString(classroomRepo.findClassroomByConsultantIdAndCourseId(1,1)));
            //System.out.println(groupRepo.findGroupByStudentIdAndCourseId(1,1));
            //System.out.println(degreeRepo.findDegreesByConsultantId(2));
            System.out.println(courseRepo.findCoursesByConsultantIdAndDegreeId(2,1));
	}
        

}
