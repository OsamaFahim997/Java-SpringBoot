package com.example.onetomany;

import com.example.onetomany.dao.AppDAO;
import com.example.onetomany.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OnetomanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnetomanyApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return args -> {
			deleteStudentById(appDAO);
		};
	}

	private void deleteStudentById(AppDAO appDAO) {
		appDAO.deleteStudentById(1);
	}

	private void updateStudentCourses(AppDAO appDAO) {
		Student student = appDAO.findCourseandStudentsByStudentId(1);
		Course course = new Course("Intro to DS");
		Course course1 = new Course("ITC");

		student.addCourse(course);
		student.addCourse(course1);

		appDAO.updateStudentCourses(student);
	}

	private void findCourseAndStudents(AppDAO appDAO) {
		Student student = appDAO.findCourseandStudentsByStudentId(1);
		System.out.println(student);
		System.out.println(student.getCourses());
	}

	private void createCourseAndStudents(AppDAO appDAO) {
		Course course = new Course("Algoooo");

		Student student1 = new Student("Osama", "Fahim", "osamafahim@gmail.com");
		Student student2 = new Student("Fahim", "Osama", "fahimosama@gmail.com");

		course.addStudent(student1);
		course.addStudent(student2);

		appDAO.saveCourse(course);
	}

	private void findCourseAndReviews(AppDAO appDAO) {
		Course course = appDAO.findCourseByIdAndReviews(10);
		System.out.println(course);
		System.out.println(course.getReviews());
	}

	private void createCourseWithReviews(AppDAO appDAO) {
		Course course = new Course("Algo and DS");
		course.addReview(new Review("Great Course"));
		course.addReview(new Review("Good course for beginners"));
		appDAO.createCourseWithReviews(course);
	}

	private void deleteCourseById(AppDAO appDAO) {
		appDAO.deleteCourseById(10);
	}

	private void deleteInstructor(AppDAO appDAO) {
		appDAO.deleteInstructor(3);
	}

	private void updateCoourse(AppDAO appDAO) {
		Course course = appDAO.findCourseByID(10);
		course.setTitle("Introduction to Data Science");
		appDAO.updateCourse(course);
	}

	private void updateInstructor(AppDAO appDAO) {
		Instructor instructor = appDAO.findById(3);
		instructor.setLastName("Doe");
		appDAO.updateInstructor(instructor);
	}

	// JOIN FETCH
	private void findInstructorByIdJoinFetch(AppDAO appDAO) {
		Instructor instructor = appDAO.findInstructorByIdJoinFetch(6);
		System.out.println(instructor);
		System.out.println(instructor.getCourses());
	}

	private void findCoursesOfInstructor(AppDAO appDAO) {
		System.out.println("Finding instructor of id: 6");
		Instructor instructor = appDAO.findById(6);
		System.out.println(instructor);

		List<Course> courses = appDAO.findCourseOfInstructor(6);
		instructor.setCourses(courses);
		System.out.println(instructor.getCourses());
	}

	private void findInstructorDetailById(AppDAO appDAO) {
		Instructor instructor = appDAO.findById(3);
		System.out.println("Finding instructor: " + instructor);

		System.out.println("Courses: " + instructor.getCourses() + "\n Done");
	}


	public void createInstructorWithCourses(AppDAO appDAO) {
		Instructor tempInstructor =
				new Instructor("Nimra", "Huda", "susan.public@luv2code.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.youtube.com",
						"Video Games");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		Course tempCourse = new Course("Introduction to Computing");

		tempInstructor.addCourse(tempCourse);

		// Save instructor and courses; This will also save courses because of cascade
		appDAO.save(tempInstructor);
	}
}
