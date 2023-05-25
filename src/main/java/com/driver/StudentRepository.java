package com.driver;

import java.util.*;

public class StudentRepository {
     private Map<String,Student> studentData=new HashMap();
     private Map<String,Teacher> teacherData=new HashMap();
     private Map<String, ArrayList<String>> teacherStudentMap=new HashMap<>();
    public void add(Student student) {
        studentData.put(student.getName(),student);
    }

    public void add(Teacher teacher) {
        teacherData.put(teacher.getName(),teacher);
    }

    public void add(String student, String teacher) {
        ArrayList<String> students=teacherStudentMap.getOrDefault(teacher,new ArrayList<String>());
        students.add(student);
        teacherStudentMap.put(teacher,students);

    }
    public Optional<Student> getStudent(String student) {
        if(studentData.containsKey(student)){
            return Optional.of(studentData.get(student));
        }
        return Optional.empty();
    }
    public Optional<Teacher> getTeacher(String teacher) {
        if(teacherData.containsKey(teacher)){
            return Optional.of(teacherData.get(teacher));
        }
        return Optional.empty();
    }

    public Teacher getTeacherByName(String name) {
        if(teacherData.containsKey(name)){
            return teacherData.get(name);
        }
        return null;
    }

    public List<String> getStudentsByTeacher(String teacher) {
       return teacherStudentMap.getOrDefault(teacher,new ArrayList<>());
    }

    public List<String> getAllStudents() {
         new ArrayList<>(studentData.keySet());
    }

    public void deleteStudent(String stud) {
        studentData.remove(stud);
    }

    public void deleteTeacher(String teacher) {
        teacherData.remove(teacher);
        teacherStudentMap.remove(teacher);
        new HashMap<>().remove();
    }

    public List<String> getAllteachers() {
        return new ArrayList<>(teacherData.keySet());
    }
}
