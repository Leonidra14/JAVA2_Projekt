package project.business.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.client.support.InterceptingHttpAccessor;

@Getter
@Setter
public class AddressDTO {
    private Long IDadd;
    private String state;
    private String city;
    private Integer house_num;
    private Integer ZIP;

    public AddressDTO(Long IDadd, String state, String city, Integer house_num, Integer ZIP) {
        this.IDadd = IDadd;
        this.state = state;
        this.city = city;
        this.house_num = house_num;
        this.ZIP = ZIP;
    }
    public AddressDTO(){

    }
}
