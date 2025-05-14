package com.lighthouse.studentapp.repository;

import com.lighthouse.studentapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>{
    List<Student> findByName(String name);
}
