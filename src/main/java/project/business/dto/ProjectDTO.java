package project.business.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ProjectDTO {
    private Long ID;
    private String name;
    private Date start_date;
    private Date end_date;

    public ProjectDTO(Long ID, String name, Date start_date, Date end_date) {
        this.ID = ID;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    public  ProjectDTO(){

    }
}
