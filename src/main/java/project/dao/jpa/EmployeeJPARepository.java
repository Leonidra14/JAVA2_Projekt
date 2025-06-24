package project.dao.jpa;

import project.dao.EmployeeRepository;
import project.domain.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface EmployeeJPARepository extends JpaRepository<Employee, Long>, EmployeeRepository {
}
