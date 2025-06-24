package project.business;

import project.dao.EmployeeRepository;
import project.dao.Employee_ProjectRepository;
import project.dao.ProjectRepository;
import project.domain.Employee;
import project.domain.Employee_Project;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class Employee_ProjectService extends AbstractCrudService<Employee_Project, Long> {
    EmployeeRepository employeeRepository;
    ProjectRepository projectRepository;

    Employee_ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository, Employee_ProjectRepository employee_projectRepository){
        super(employee_projectRepository);
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }


    //Completable future - spusteni na jinem nez hlavnim vlakne
    public CompletableFuture<Employee_Project> add_employee_to_project(Employee_Project employee_project) {
        return CompletableFuture.supplyAsync(() -> create(employee_project));
    }

    public Employee_Project change_endDate_on_project(Employee_Project employee_project, Date date) throws RuntimeException {
        if(date.before(new Date())){
            throw new RuntimeException("Chyba:Nové datum konce práce nemůže být menší, než aktuální");
        }
        Optional<Employee_Project> check = readById(employee_project.getId());
        if(check.isEmpty()) {
            throw new RuntimeException("Chyba:ID neexistuje");
        }
        var entityDB = check.get();
        entityDB.setDate_to(new Date());
        return update(entityDB);

    }

    public Long number_of_working_days(Employee_Project employee_project) throws RuntimeException{
        Optional<Employee_Project> check = readById(employee_project.getId());
        if(check.isEmpty()) {
            throw new RuntimeException("Chyba:ID neexistuje");
        }
        var entityDB = check.get();
        Date date = entityDB.getDate_from();
        var actual_date = new Date();
        Long diffInDays = TimeUnit.MILLISECONDS.toDays(date.getTime() - actual_date.getTime());;
        return  diffInDays;
    }

    public Employee_Project delete_employee_from_project (Employee_Project employee_project){
        Optional<Employee_Project> check = readById(employee_project.getId());
        if(check.isEmpty()) {
            throw new RuntimeException("Chyba:ID neexistuje");
        }
        var entityDB = check.get();
        entityDB.setDate_to(new Date());
        return entityDB;
    }
    public Collection<Employee> list_of_employees_added_to_project(Long id) throws RuntimeException{
        //najdeme v db projekt
        var project = projectRepository.findById(id);
        //kontrola ID
        if(project.isEmpty()){
            throw new RuntimeException("list_of_employees_added_to_project: dane id neexistuje");
        }
        //vrati employee-project
        var filtered_employee_projects = repository.findAll().stream().filter( employee_project -> {
            return employee_project.getProject().getId().equals(id);
        }).toList();

        //vsechny employee
        var employees = new ArrayList<Employee>(employeeRepository.findAll());
        //nastaveni flagu jestli spada do vzbrane skupiny + mazani
        for (int i = 0; i < employees.size(); ) {
            boolean flag = true;
            for(var project_empl : filtered_employee_projects){
                if(project_empl.getEmployee().getId().equals(employees.get(i).getId())){
                    flag = false;
                    break;
                }
            }

            //mazani
            if(flag){
                employees.remove(i);
            }
            else {
                ++i;
            }
        }


        return employees;
    }

}
