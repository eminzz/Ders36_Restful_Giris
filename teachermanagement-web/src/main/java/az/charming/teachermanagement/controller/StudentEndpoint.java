package az.charming.teachermanagement.controller;

import az.charming.teachermanagement.controller.dto.SchoolResponseDto;
import az.charming.teachermanagement.controller.dto.StudentResponseDto;
import az.charming.teachermanagement.controller.dto.TeacherResponseDto;
import az.charming.teachermanagement.dto.SchoolDto;
import az.charming.teachermanagement.dto.StudentDto;
import az.charming.teachermanagement.dto.TeacherDto;
import az.charming.teachermanagement.entity.StudentEntity;
import az.charming.teachermanagement.entity.TeacherEntity;
import az.charming.teachermanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class StudentEndpoint {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/students", method= {RequestMethod.GET})
    public List<StudentResponseDto> index(@RequestParam (required = false) String name,
                                  @RequestParam(required = false) String surname,
                                  @RequestParam(required = false) Integer age,
                                  @RequestParam(required = false) BigDecimal scholarship
    ){
        List<StudentDto> list= studentService.findAll(
                name,
                surname,
                age,
                scholarship
        );
        List<StudentResponseDto> result = new ArrayList<>();
        for(StudentDto st: list){
            result.add(StudentResponseDto.insatance(st));
        }
        return result;
    }
}
