package com.namndt.webschool.controllers;

import com.namndt.webschool.Repositories.ClassRepository;
import com.namndt.webschool.Repositories.CoursesRepository;
import com.namndt.webschool.Repositories.PersonRepository;
import com.namndt.webschool.model.Course;
import com.namndt.webschool.model.EazyClass;
import com.namndt.webschool.model.Person;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @GetMapping("/displayClasses")
    public String displayClasses(Model model){
        List<EazyClass> eazyClasses = classRepository.findAll();
        model.addAttribute("eazyClass", new EazyClass());
        model.addAttribute("eazyClasses", eazyClasses);
        return "classes.html";
    }

    @PostMapping("/addNewClass")
    public String addNewClass(@ModelAttribute("eazyClass") EazyClass eazyClass){
        classRepository.save(eazyClass);
        return "redirect:/admin/displayClasses";
    }

    @GetMapping("/deleteClass")
    public String deleteClass(@RequestParam("id") int classId){
        Optional<EazyClass> eazyClass = classRepository.findById(classId);
        if (eazyClass.get() == null || !(eazyClass.get().getClassId() > 0)) {
            return "redirect:/admin/displayClasses";
        }
        eazyClass.get().getPersons().stream().forEach(e -> {
            e.setEazyClass(null);
            personRepository.save(e);
        });
        classRepository.deleteById(classId);
        return "redirect:/admin/displayClasses";

    }

    @GetMapping("/displayStudents")
    public String displayStudents(@RequestParam("classId") int eazyClassId, Model model, HttpSession session,
                                  @RequestParam(value = "error", required = false) boolean error){
        String errorMsg = "";
        Optional<EazyClass> eazyClass = classRepository.findById(eazyClassId);
        model.addAttribute("eazyClass", eazyClass.get());
        model.addAttribute("person", new Person());
        if(error){
            errorMsg = "Invalid email!";
        }
        model.addAttribute("errorMessage", errorMsg);
        session.setAttribute("eazyClass", eazyClass.get());
        return "students.html";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("person") Person email, HttpSession session){
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person person = personRepository.readByEmail(email.getEmail());
        if (person == null || !(person.getPersonId() > 0)) {
            return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId()
                    + "&error=true";
        }
        person.setEazyClass(eazyClass);
        personRepository.save(person);
        eazyClass.getPersons().add(person);
        session.setAttribute("eazyClass", eazyClass);
        return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId();
    }

    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam("personId") int personId, HttpSession session){
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById(personId);
        if (person.get() == null || !(person.get().getPersonId() > 0)) {
            return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId();
        }

        person.get().setEazyClass(null);
        personRepository.save(person.get());
        eazyClass.getPersons().remove(person.get());
        return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId();
    }

    @GetMapping("/displayCourses")
    public String dislayCourse(Model model, Authentication authentication){
        model.addAttribute("course", new Course());
        List<Course> courses = coursesRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses_secure";
    }

    @PostMapping("/addNewCourse")
    public String addNewCourse(@Valid @ModelAttribute("course") Course course){
        coursesRepository.save(course);
        return "redirect:/admin/displayCourses";
    }

    @GetMapping("/viewStudents")
    public String detailsCourse(@RequestParam("id") int courseId, Model model,
                                HttpSession session, @RequestParam(value = "error", required = false) boolean error){
        Optional<Course> courses = coursesRepository.findById(courseId);
        if (courses.get() == null || !(courses.get().getCourseId() > 0)) {
            return "redirect:/admin/displayCourses";
        }
        model.addAttribute("person", new Person());
        model.addAttribute("courses", courses.get());
        session.setAttribute("course", courses.get());
        if (error) {
            String errorMessage = "Invalid Student";
            model.addAttribute("errorMessage", errorMessage);
            return "course_students";
        }
        return "course_students";
    }

    @PostMapping("/addStudentToCourse")
    public String addStudentToCourse(@ModelAttribute("person") Person person, HttpSession session){
        Person personChecked = personRepository.readByEmail(person.getEmail());
        Course course = (Course) session.getAttribute("course");
        if (personChecked == null || !(personChecked.getPersonId() > 0)) {
            return "redirect:/admin/viewStudents?id=" + course.getCourseId() +"&error=true";
        }

        personChecked.getCourses().add(course);
        personRepository.save(personChecked);
        course.getPersons().add(person);
        session.setAttribute("course", course);
        return "redirect:/admin/viewStudents?id=" + course.getCourseId();
    }

    @GetMapping("/deleteStudentFromCourse")
    public String deleteStudentFromCourse(@RequestParam("personId") int personId, HttpSession session){
        Optional<Person> personEntity = personRepository.findById(personId);
        Course course = (Course) session.getAttribute("course");
        if (personEntity.get() == null || !(personEntity.get().getPersonId() > 0)) {
            return "redirect:/admin/viewStudents?id=" + course.getCourseId();
        }
        personEntity.get().getCourses().remove(course);
        course.getPersons().remove(personEntity);
        personRepository.save(personEntity.get());
        session.setAttribute("course", course);
        return "redirect:/admin/viewStudents?id=" + course.getCourseId();
    }
}
