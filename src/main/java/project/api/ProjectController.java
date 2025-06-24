package project.api;


import project.business.ProjectService;
import project.business.dto.ProjectDTO;
import project.domain.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projekt")
public class ProjectController extends AbstractCrudController<Project, ProjectDTO, Long>{
    public ProjectController(ProjectService service){
        super(service,
                //prevod
                entity -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setID(entity.getId());
                    projectDTO.setName(entity.getProj_name());
                    projectDTO.setEnd_date(entity.getDate_to());
                    projectDTO.setStart_date(entity.getDate_from());
                    return projectDTO;
                },
                dto -> {
                    System.out.println(dto.getEnd_date());
                    System.out.println(dto.getStart_date());
                    if(dto.getName() == null || dto.getEnd_date() == null || dto.getStart_date() == null )
                        throw new RuntimeException("chybne parametry");
                    Project e = new Project();
                    e.setId(dto.getID());
                    e.setProj_name(dto.getName());
                    e.setDate_to(dto.getEnd_date());
                    e.setDate_from(dto.getStart_date());
                    return e;
                }
        );
    }
}
