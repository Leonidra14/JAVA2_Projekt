package project.dao.jpa;

import project.dao.Employee_ProjectRepository;
import project.domain.Employee_Project;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface Employee_ProjectJPARepository extends JpaRepository<Employee_Project, Long>, Employee_ProjectRepository {
}
