package com.example.onetomany.dao;

import com.example.onetomany.entity.Course;
import com.example.onetomany.entity.Instructor;
import com.example.onetomany.entity.InstructorDetail;
import com.example.onetomany.entity.Student;
import jakarta.persistence.Column;

import java.util.List;

public interface AppDAO {

    void save(Instructor instructor);
    Instructor findById(int id);
    void delete(int id);

    InstructorDetail findInstructorByInstructorDetail(int id);
    void deleteInstructorDetail(int id);

    List<Course> findCourseOfInstructor(int id);
    Instructor findInstructorByIdJoinFetch(int id);

    void updateInstructor(Instructor instructor);
    void deleteInstructor(int id);

    Course findCourseByID(int id);
    void updateCourse(Course course);
    void deleteCourseById(int id);

    void createCourseWithReviews(Course course);
    Course findCourseByIdAndReviews(int id);

    void saveCourse(Course course);
    Student findStudentById(int id);

    Course findCourseandStudentsByCourseId(int id);
    Student findCourseandStudentsByStudentId(int id);

    void updateStudentCourses(Student student);
    void deleteStudentById(int id);
}
