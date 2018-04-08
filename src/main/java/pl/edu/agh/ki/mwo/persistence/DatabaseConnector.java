package pl.edu.agh.ki.mwo.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;

public class DatabaseConnector {
	
	protected static DatabaseConnector instance = null;
	
	public static DatabaseConnector getInstance() {
		if (instance == null) {
			instance = new DatabaseConnector();
		}
		return instance;
	}
	
	Session session;

	protected DatabaseConnector() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void teardown() {
		session.close();
		HibernateUtil.shutdown();
		instance = null;
	}
	
	public Iterable<School> getSchools() {
		
		String hql = "FROM School";
		Query query = session.createQuery(hql);
		List schools = query.list();
		
		return schools;
	}
	
	public Iterable<SchoolClass> getSchoolClasses() {
		
		String hql = "FROM SchoolClass";
		Query query = session.createQuery(hql);
		List schoolClasses = query.list();
		
		return schoolClasses;
	}
	
	public Iterable<Student> getStudents() {
		
		String hql = "FROM Student";
		Query query = session.createQuery(hql);
		List students = query.list();
		
		return students;
	}
	
	public School getSchool(String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		School school =  (School) query.uniqueResult();
		
		return school;
	}
	
	public SchoolClass getSchoolClass(String schoolClassId) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		SchoolClass schoolClass =  (SchoolClass) query.uniqueResult();
		
		return schoolClass;
	}
	
	public Student getStudent(String studentId) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		Student student =  (Student) query.uniqueResult();
		
		return student;
	}
	
	public void updateSchool(String schoolId, String address, String name) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		School school =  (School) query.uniqueResult();
		school.setAddress(address);
		school.setName(name);
		Transaction transaction = session.beginTransaction();
		session.update(school);
		transaction.commit();
	}
	
	public void updateSchoolClass(String schoolClassId, String profile, int startYear, int currentYear) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		SchoolClass schoolClass = (SchoolClass) query.uniqueResult();
		schoolClass.setProfile(profile);
		schoolClass.setStartYear(startYear);
		schoolClass.setCurrentYear(currentYear);
		Transaction transaction = session.beginTransaction();
		session.update(schoolClass);
		transaction.commit();
	}
	
	public void updateStudent(String studentId, String name, String surname, String pesel) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		Student student = (Student) query.uniqueResult();
		student.setName(name);
		student.setSurname(surname);
		student.setPesel(pesel);
	
		Transaction transaction = session.beginTransaction();
		session.update(student);
		transaction.commit();
	}
	
	public void addSchool(School school) {
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
	}
	
	public void addSchoolClass(SchoolClass schoolClass) {
		Transaction transaction = session.beginTransaction();
		session.save(schoolClass);
		transaction.commit();
	}
	
	public void addStudent(Student student) {
		Transaction transaction = session.beginTransaction();
		session.save(student);
		transaction.commit();
	}
	
	
	public void deleteSchool(String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (School s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
	
	public void deleteSchoolClass(String schoolClassId) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<SchoolClass> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (SchoolClass s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
	
	public void deleteStudent(String studentId) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		List<Student> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Student s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
}
