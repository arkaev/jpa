package ru.naumen.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.naumen.entities.Course;
import ru.naumen.entities.Student;
import ru.naumen.model.CourseDao;
import ru.naumen.model.ScholarshipDao;
import ru.naumen.model.StudentDao;

/**
 * @author aarkaev
 */
@RestController
public class StudentsController {

    @Autowired
    private StudentDao _studentDao;

    @Autowired
    private CourseDao _courseDao;

    @Autowired
    private ScholarshipDao _scholarshipDao;

    @RequestMapping(value = "/allstudents", method = RequestMethod.GET)
    public List<Student> listAllStudents() {
        return _studentDao.findAll();
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public List<Course> listAllCourses() {
        return _courseDao.findAll();
    }

    @RequestMapping(value = "/scholarship/{id}", method = RequestMethod.GET)
    public double getScholarship(@PathVariable("id") int id) {
        return _scholarshipDao.findByStudentId(id);
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public Student get(@PathVariable("id") int id) {
        return _studentDao.find(id).orElse(null);
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Student data) {
        _studentDao.create(data);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) {
        Optional<Student> student = _studentDao.find(id);
        if (student.isPresent()) {
            _studentDao.delete(student.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
