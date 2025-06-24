package project.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Address implements BasicEntity<Long>{
    @Id
    @Getter
    @Setter
    @GeneratedValue
    Long Id;

    @Column
    @Getter
    @Setter
    String state;

    @Column
    @Getter
    @Setter
    String city;

    @Column
    @Getter
    @Setter
    Integer house_no;

    @Column
    @Getter
    @Setter
    Integer ZIP;

    @OneToMany(mappedBy = "address")
    Collection<Employee> employees = new ArrayList<Employee>();

    @Override
    public Long getId(){
        return Id;
    }

}
