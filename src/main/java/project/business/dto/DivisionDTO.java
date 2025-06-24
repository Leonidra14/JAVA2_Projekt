package project.business.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DivisionDTO {
    private  Long ID;
    private String comp;

    public DivisionDTO(Long ID, String comp) {
        this.ID = ID;
        this.comp = comp;
    }
    public  DivisionDTO(){

    }
}
