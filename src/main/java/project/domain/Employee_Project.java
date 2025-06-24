package project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Employee_Project implements BasicEntity<Long> {
    @Id
    @Getter
    @Setter
    //@GeneratedValue
    Long Id;

    @Getter
    @Column
    @Setter
    Date date_from;

    @Getter
    @Setter
    @Column
    Date date_to;

    @ManyToOne
    @Getter
    @Setter
    Employee employee;

    @ManyToOne
    @Getter
    @Setter
    Project project;

    @Override
    public Long getId(){
        return Id;
    }

}
