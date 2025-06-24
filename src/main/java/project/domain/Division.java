package project.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Division implements BasicEntity<Long>{
    @Id
    @Getter
    @Setter
    @GeneratedValue
    Long Id;

    @Column
    @Getter
    @Setter
    String competencies;

    @OneToMany(mappedBy = "division")
    Collection<Employee> employees = new ArrayList<Employee>();

    @Override
    public Long getId(){
        return Id;
    }

}

