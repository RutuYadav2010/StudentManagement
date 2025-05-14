package com.lighthouse.studentapp.controller;

import com.lighthouse.studentapp.model.Student;
import com.lighthouse.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:8000")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return ResponseEntity.ok(Map.of("message", "Student added successfully"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/delete-student")
    public ResponseEntity<Map<String, String>> deleteStudent(@RequestBody Map<String, String> body) {
        Long id;
        try {
            id = Long.parseLong(body.get("id"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid or missing student ID"));
        }

        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Student not found with ID " + id));
        }

        studentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
    }

    @PutMapping("/update-student/{id}")
    public ResponseEntity<Map<String, String>> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (!optionalStudent.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Student not found"));
        }

        Student existingStudent = optionalStudent.get();
        existingStudent.setName(studentDetails.getName());
        existingStudent.setAge(studentDetails.getAge());
        existingStudent.setCourse(studentDetails.getCourse());
        existingStudent.setEmail(studentDetails.getEmail());

        studentRepository.save(existingStudent);
        return ResponseEntity.ok(Map.of("message", "Student updated successfully"));
    }
}


