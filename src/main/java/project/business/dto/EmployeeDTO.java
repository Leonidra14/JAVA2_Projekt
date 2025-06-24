package project.business.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
@Getter
@Setter
public class EmployeeDTO {
    private Long ID;
    private String fname;
    private String lname;

    private  Character sex;

    private String email;

    private Integer work_phone;

    public EmployeeDTO(){

    }

    public EmployeeDTO(Long ID, String fname, String lname, Character sex, String email, Integer work_phone) {
        this.ID = ID;
        this.fname = fname;
        this.lname = lname;
        this.sex = sex;
        this.email = email;
        this.work_phone = work_phone;
    }
}
