package project.business;

import org.springframework.stereotype.Service;
import project.dao.AddressRepository;
import project.domain.Address;

@Service
public class AddressService extends AbstractCrudService<Address, Long>{
    AddressService(AddressRepository r){
        super(r);
    }
}
