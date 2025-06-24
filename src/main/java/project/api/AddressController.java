package project.api;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.ProjektJavaApplication;
import project.business.AddressService;
import project.business.dto.AddressDTO;
import project.domain.Address;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Log4j2
@RestController
@RequestMapping("/adresa")
public class AddressController extends AbstractCrudController<Address, AddressDTO, Long> {

    LocalDateTime now = LocalDateTime.now();
    private static final Logger logger = LogManager.getLogger(ProjektJavaApplication.class);


    public AddressController(AddressService service) {
        super(service,
                //prevod
                entity -> {
                    log.debug(entity.getId());
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setIDadd(entity.getId());
                    addressDTO.setCity(entity.getCity());
                    addressDTO.setState(entity.getState());
                    addressDTO.setHouse_num(entity.getHouse_no());
                    addressDTO.setZIP(entity.getZIP());

                    return addressDTO;
                },
                dto -> {
                    //System.out.println(dto.getIDadd() );
                    System.out.println(dto.getState());

                    if(dto.getState() == null || dto.getCity() == null || dto.getHouse_num() == null || dto.getZIP() == null ) {
                        throw new RuntimeException("chybne parametry");
                    }
                    Address e = new Address();
                    e.setId(dto.getIDadd());
                    e.setState(dto.getState());
                    e.setCity(dto.getCity());
                    e.setHouse_no(dto.getHouse_num());
                    e.setZIP(dto.getZIP());
                    return e;
                }
        );
        logger.debug("prace s entitou" + now);
        System.out.println("dto.getDate_from()" );
    }
}
