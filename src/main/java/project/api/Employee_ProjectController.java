package project.api;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import project.ProjektJavaApplication;
import project.business.EmployeeService;
import project.business.Employee_ProjectService;
import project.business.ProjectService;
import project.business.dto.Employee_ProjectDTO;
import project.domain.Employee_Project;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
@RequestMapping("/prace")
public class Employee_ProjectController extends AbstractCrudController<Employee_Project, Employee_ProjectDTO, Long> {
    private static final Logger logger = LogManager.getLogger(ProjektJavaApplication.class);
    private final LocalDateTime now = LocalDateTime.now();

    public Employee_ProjectController(Employee_ProjectService service, EmployeeService employeeService, ProjectService projectService) {
        super(service,
                // převod
                entity -> {
                    log.debug(entity.getId());
                    Employee_ProjectDTO employee_projectDTO = new Employee_ProjectDTO();
                    employee_projectDTO.setIDemp_proj(entity.getId());
                    employee_projectDTO.setIDemp(entity.getEmployee().getId());
                    employee_projectDTO.setIDproj(entity.getProject().getId());
                    employee_projectDTO.setDate_to(entity.getDate_to());
                    employee_projectDTO.setDate_from(entity.getDate_from());
                    return employee_projectDTO;
                },
                dto -> {
                    System.out.println("dto.getDate_from()");
                    System.out.println(dto.getDate_from());
                    System.out.println(dto.getIDproj());
                    System.out.println(dto.getDate_to());
                    System.out.println(dto.getIDemp());
                    System.out.println(dto.getIDproj());
                    logger.debug(dto.getIDproj());
                    logger.debug(dto.getDate_from());
                    logger.debug(dto.getDate_to());
                    logger.debug(dto.getIDemp_proj());
                    logger.debug(dto.getIDemp());

                    if (dto.getDate_to() == null || dto.getDate_from() == null ) {
                        throw new RuntimeException("chybne parametry");
                    }

                    Employee_Project e = new Employee_Project();
                    e.setId(dto.getIDemp_proj());
                    e.setDate_to(dto.getDate_to());
                    e.setDate_from(dto.getDate_from());

                    System.out.println(dto.getDate_from());
                    System.out.println(dto.getIDproj());
                    System.out.println(dto.getDate_to());
                    System.out.println(dto.getIDemp());
                    System.out.println(dto.getIDproj());

                    if(dto.getIDproj() == null){

                    }

                    e.setEmployee(employeeService.readById(dto.getIDemp()).orElseThrow());
                    e.setProject(projectService.readById(dto.getIDproj()).orElseThrow());

                    return e;
                }
        );
        logger.debug("Práce s entitou v čase: " + now);
    }

    @GetMapping("/tretifunkce/{id}/{date}")
    public Employee_Project druhafunkce(@RequestBody Employee_ProjectDTO e, Date date) throws InterruptedException, ExecutionException {
        CompletableFuture<Employee_Project> futureResult = ((Employee_ProjectService) service).add_employee_to_project(this.toEntityConverter.apply(e));
        Employee_Project result = futureResult.join();
        return result;
    }

    @GetMapping("/funkce/{id}")
    public CompletableFuture<Employee_Project> add_employee_to_project(@RequestBody Employee_ProjectDTO e) {
        return ((Employee_ProjectService) service).add_employee_to_project(toEntityConverter.apply(e));
    }
}
