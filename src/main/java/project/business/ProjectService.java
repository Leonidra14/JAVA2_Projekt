package project.business;

import project.dao.ProjectRepository;
import project.domain.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends AbstractCrudService<Project, Long>{

    ProjectService(ProjectRepository r){
        super(r);
    }

}
