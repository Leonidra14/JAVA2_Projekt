package project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
public class Project implements BasicEntity<Long>{

    @Id
    @Setter
    @GeneratedValue
    Long Id;

    @Column
    @Getter
    @Setter
    String proj_name;

    @Column
    @Getter
    @Setter
    Date date_from;

    @Column
    @Getter
    @Setter
    Date date_to;

    @Column
    @Getter
    @Setter


    @OneToMany (mappedBy = "project")
    Collection<Employee_Project> employee_projects = new ArrayList<Employee_Project>();

    @Override
    public Long getId(){
        return Id;
    }



}
