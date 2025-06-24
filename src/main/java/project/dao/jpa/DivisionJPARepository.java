package project.dao.jpa;

import project.dao.DivisionRepository;
import project.domain.Division;
import project.domain.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface DivisionJPARepository extends JpaRepository<Division, Long>, DivisionRepository {
}
