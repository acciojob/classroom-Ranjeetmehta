package com.driver;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentController {

    private StudentService studentService=new StudentService();

    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestBody Student student){
     studentService.addStudent(student);
        return new ResponseEntity<>("New student added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-teacher")
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher){
    studentService.addTeacher(teacher);
        return new ResponseEntity<>("New teacher added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-student-teacher-pair")
    public ResponseEntity<String> addStudentTeacherPair(@RequestParam String student, @RequestParam String teacher) {
        try {
            studentService.addStudentTeacherPair(student,teacher);
            return new ResponseEntity<>("New student-teacher pair added successfully", HttpStatus.CREATED);
        }
        catch (TeacherInvalidException ex) {
            return new ResponseEntity<>("Teacher name is invalid:" + teacher, HttpStatus.NOT_FOUND);
        }

        catch (StudentNameInvalidException ex) {
            return new ResponseEntity<>("Student name is invalid:" + student, HttpStatus.NOT_FOUND);
        }
        catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-student-by-name/{name}")
    public ResponseEntity<Student> getStudentByName(@PathVariable String name) {
        try {
            Student student = studentService.getStudent(name); // Assign student by calling service layer method

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (StudentNameInvalidException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-teacher-by-name/{name}")
    public ResponseEntity<Teacher> getTeacherByName(@PathVariable String name) {
        Teacher teacher = studentService.getTeacherByName(name);// Assign student by calling service layer method
        if (Objects.nonNull(teacher)) {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-students-by-teacher-name/{teacher}")
    public ResponseEntity<List<String>> getStudentsByTeacherName(@PathVariable String teacher){
        List<String> students = studentService.getStudentsByTeacherName(teacher); // Assign list of student by calling service layer method

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/get-all-students")
    public ResponseEntity<List<String>> getAllStudents(){
        List<String> students =studentService.getAllStudents(); // Assign list of student by calling service layer method

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @DeleteMapping("/delete-teacher-by-name")
    public ResponseEntity<String> deleteTeacherByName(@RequestParam String teacher){
        studentService.deleteTeacher(teacher);
        return new ResponseEntity<>(teacher + " removed successfully", HttpStatus.CREATED);
    }
    @DeleteMapping("/delete-all-teachers")
    public ResponseEntity<String> deleteAllTeachers(){
        studentService.deleteAllTeachers();

        return new ResponseEntity<>("All teachers deleted successfully", HttpStatus.OK);
    }
}
