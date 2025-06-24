package project.business;

import org.springframework.stereotype.Service;
import project.dao.DivisionRepository;
import project.domain.Division;

@Service
public class DivisionService extends AbstractCrudService<Division, Long>{
    DivisionService(DivisionRepository r){
        super(r);
    }
}
