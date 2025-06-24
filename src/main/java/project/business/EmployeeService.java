package project.business;

import org.springframework.stereotype.Service;
import project.dao.EmployeeRepository;
import project.domain.Employee;


@Service
public class EmployeeService extends AbstractCrudService<Employee, Long> {
    EmployeeService(EmployeeRepository r){
        super(r);
    }

}
