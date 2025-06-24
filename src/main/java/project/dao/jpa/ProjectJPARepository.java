package project.dao.jpa;

import project.dao.ProjectRepository;
import project.domain.Project;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface ProjectJPARepository extends JpaRepository<Project, Long>, ProjectRepository {
}
