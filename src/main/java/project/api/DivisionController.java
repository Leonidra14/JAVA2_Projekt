package project.api;



import project.business.DivisionService;
import project.business.dto.DivisionDTO;
import project.domain.Division;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oddeleni")
public class DivisionController extends AbstractCrudController<Division, DivisionDTO, Long>{
    public DivisionController(DivisionService service){
        super(service,
                //prevod
                entity -> {

                    DivisionDTO divisionDTO = new DivisionDTO();
                    divisionDTO.setID(entity.getId());
                    divisionDTO.setComp(entity.getCompetencies());

                    return divisionDTO;
                },
                dto -> {
                    System.out.println("vypis" );
                    System.out.println(dto.getComp());
                    if( dto.getComp() == null )
                        throw new RuntimeException("chybne parametry");
                    Division e = new Division();
                    e.setId(dto.getID());
                    e.setCompetencies(dto.getComp());

                    return e;
                }
        );
    }
}

