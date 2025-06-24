package project.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.business.EmployeeService;
import project.business.Employee_ProjectService;
import project.business.dto.EmployeeDTO;
import project.domain.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Log4j2
@RestController
@RequestMapping("/zamestnanci")
public class EmployeeController extends AbstractCrudController<Employee, EmployeeDTO, Long> {
    Employee_ProjectService employeeProjectService;
    public EmployeeController(EmployeeService service, Employee_ProjectService employeeProjectService) {
        super(service, //prevod
                entity -> {
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setID(entity.getId());
                    employeeDTO.setFname(entity.getFirst_name());
                    employeeDTO.setLname(entity.getLast_name());
                    employeeDTO.setSex(entity.getSex());
                    employeeDTO.setEmail(entity.getEmail());
                    employeeDTO.setWork_phone(entity.getWork_phone());
                    return employeeDTO;
                }, dto -> {
                    log.info(dto.getFname(), dto.getLname(), dto.getEmail(), dto.getSex(), dto.getWork_phone());
                    if (dto.getFname() == null || dto.getLname() == null || dto.getSex() == null || dto.getEmail() == null ||  dto.getWork_phone() == null)
                        throw new RuntimeException("chybne parametry");
                    Employee e = new Employee();
                    e.setId(dto.getID());
                    e.setFirst_name(dto.getFname());
                    e.setLast_name(dto.getLname());
                    e.setSex(dto.getSex());
                    e.setEmail(dto.getEmail());
                    e.setWork_phone(dto.getWork_phone());
                    return e;
                });

        this.employeeProjectService = employeeProjectService;
    }
    @GetMapping("/prvnifunkce/{id}")
    public Collection<EmployeeDTO> prvnifunkce(@PathVariable Long id){
        var tmp = employeeProjectService.list_of_employees_added_to_project(id);
        return tmp.stream().map( e -> {
            return toDtoConverter.apply(e);
        }).toList();
    }
}
