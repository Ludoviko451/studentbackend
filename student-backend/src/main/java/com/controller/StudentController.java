package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exception.ResourceNotFoundException;
import com.model.Student;
import com.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/student")
    public List<Student> showStudents(){
        return studentRepository.findAll();
    }

    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student){
        return studentRepository.save(student);
    }

    @GetMapping("/student/{id_student}")
    public ResponseEntity<Student> showStudentByID(@PathVariable Long id_student){
        Student student = studentRepository.findById(id_student)
            .orElseThrow(() -> new ResourceNotFoundException("EL CLIENTE CON ESE ID NO EXISTE:" + id_student));
        return ResponseEntity.ok(student);
    }

    @PutMapping("/student/{id_student}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id_student, @RequestBody Student studentRequest){
        Student student = studentRepository.findById(id_student)
            .orElseThrow(() -> new ResourceNotFoundException("EL CLIENTE CON ESE ID NO EXISTE:" + id_student));
        
        student.setFirst_name(studentRequest.getFirst_name());
        student.setLast_name(studentRequest.getLast_name());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());

        Student updatedStudent = studentRepository.save(student);

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/student/{id_student}")
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id_student){
        Student student = studentRepository.findById(id_student)
            .orElseThrow(() -> new ResourceNotFoundException("EL CLIENTE CON ESE ID NO EXISTE:" + id_student));
        
        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
