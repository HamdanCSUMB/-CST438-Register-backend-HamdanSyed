package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    // declare the following method to return a single Student object
    // default JPA behavior that findBy methods return List<Student> except for findById.
    @Query("select s from Student s where s.email=:email")
    public Student findByEmail(String email);

    @Query("select s from Student s where s.student_id=:id")
    public Student findByStudentId(int id);

    Student save(Student s);
}
