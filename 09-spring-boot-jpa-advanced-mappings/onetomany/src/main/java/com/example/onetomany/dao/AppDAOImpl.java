package com.example.onetomany.dao;

import com.example.onetomany.entity.Course;
import com.example.onetomany.entity.Instructor;
import com.example.onetomany.entity.InstructorDetail;
import com.example.onetomany.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Instructor instructor = entityManager.find(Instructor.class, id);
        entityManager.remove(instructor);
    }

    @Override
    public InstructorDetail findInstructorByInstructorDetail(int id) {
        InstructorDetail detail = entityManager.find(InstructorDetail.class, id);
        return detail;
    }

    @Override
    @Transactional
    public void deleteInstructorDetail(int id) {
        InstructorDetail detail = entityManager.find(InstructorDetail.class, id);

        // break bi directional link
        detail.getInstructor().setInstructorDetail(null);

        entityManager.remove(detail);
    }

    public List<Course> findCourseOfInstructor(int id) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c where instructor.id = :id", Course.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        TypedQuery<Instructor> query = entityManager.createQuery("select i from Instructor i " +
                                                                    "JOIN FETCH i.courses " +
                                                                    "JOIN FETCH i.instructorDetail " +
                                                                    "where i.id = :id", Instructor.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateInstructor(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Course findCourseByID(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructor(int id) {
        Instructor instructor = entityManager.find(Instructor.class, id);
        List<Course> instructorCourses = instructor.getCourses();

        // break association of the courses with instructor
        for (Course course: instructorCourses) {
            course.setInstructor(null);
        }
        entityManager.remove(instructor);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course course = entityManager.find(Course.class, id);
        course.setInstructor(null);
        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void createCourseWithReviews(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseByIdAndReviews(int id) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c "
                                                                + "JOIN FETCH c.reviews "
                                                                + "where c.id = :id", Course.class);
        query.setParameter("id", id);
        Course course = query.getSingleResult();
        return course;
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Student findStudentById(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public Course findCourseandStudentsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c "
                + "JOIN FETCH c.students "
                + "where c.id = :id", Course.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Student findCourseandStudentsByStudentId(int id) {
        TypedQuery<Student> query = entityManager.createQuery("select s from Student s "
                + "JOIN FETCH s.courses "
                + "where s.id = :id", Student.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateStudentCourses(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        Student student = entityManager.find(Student.class, id);
        entityManager.remove(student);
    }
}
