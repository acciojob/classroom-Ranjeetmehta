package com.driver;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private StudentRepository studentRepository=new StudentRepository();

    public void addStudent(Student student) {
        studentRepository.add(student);
    }

    public void addTeacher(Teacher teacher) {
     studentRepository.add(teacher);

    }

    public void addStudentTeacherPair(String student, String teacher) throws  TeacherInvalidException, StudentNameInvalidException {
        Optional<Student> studentOpt=studentRepository.getStudent(student);
        Optional<Teacher> teacherOpt=studentRepository.getTeacher(teacher);
        if(studentOpt.isEmpty()) {
            throw new StudentNameInvalidException(student);
        }
        if(teacherOpt.isEmpty()) {
            throw new TeacherInvalidException();
        }
        Teacher teacherobj1=teacherOpt.get();
        teacherobj1.setNumberOfStudents(teacherobj1.getNumberOfStudents()+1);
        studentRepository.add(teacherobj1);
            studentRepository.add(student, teacher);
        }

    public Student getStudent(String name)  throws StudentNameInvalidException{
        Optional<Student> studentOpt =studentRepository.getStudent(name);
        if(studentOpt.isPresent()){
            return studentOpt.get();

    }
        throw new StudentNameInvalidException(name);
}

    public Teacher getTeacherByName(String name) {
return studentRepository.getTeacherByName(name);
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        return studentRepository.getStudentsByTeacher(teacher);
    }

    public List<String> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public void deleteTeacher(String teacher) {
        List<String> students=getStudentsByTeacherName(teacher);
        studentRepository.deleteTeacher(teacher);
        for(String stud: students){
            studentRepository.deleteStudent(stud);
        }
    }

    public void deleteAllTeachers() {
        List<String> teachers=studentRepository.getAllteachers();
        for(String teacher: teachers){
            deleteTeacher(teacher);
        }
    }
}
