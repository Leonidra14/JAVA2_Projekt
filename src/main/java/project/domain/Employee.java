package project.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
public class Employee implements BasicEntity<Long> {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long Id;

    @Column
    @Getter
    @Setter
    private String first_name;

    @Column
    @Getter
    @Setter
    private String last_name;

    @Column
    @Getter
    @Setter
    private Date date_of_birth;

    @Column
    @Getter
    @Setter
    private Character sex;


    @Column
    @Getter
    @Setter
    private Integer salary;

    @Column
    @Getter
    @Setter
    private Integer personal_phone;

    @Column
    @Getter
    @Setter
    private Integer work_phone;


    @Column
    @Getter
    @Setter
    private String email;



    @ManyToOne
    @Getter
    Address address;

    @ManyToOne
    @Getter
    Address division;

    @Override
    public Long getId(){
        return Id;
    }
    @OneToMany (mappedBy = "employee")
    Collection<Employee_Project> employee_projects = new ArrayList<Employee_Project>();

    //davam vzdy nazev tabulky s malym

}
