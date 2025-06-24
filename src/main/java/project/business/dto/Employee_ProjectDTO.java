package project.business.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class Employee_ProjectDTO implements Serializable {
    private Long IDemp_proj;
    private Long IDproj;
    private Long IDemp;
    private Date date_to;
    private Date date_from;

    public Employee_ProjectDTO(Long IDemp_proj, Long IDproj, Long IDemp, Date date_to, Date date_from) {
        this.IDemp_proj = IDemp_proj;
        this.IDproj = IDproj;
        this.IDemp = IDemp;
        this.date_to = date_to;
        this.date_from = date_from;
    }

    public Employee_ProjectDTO(){

    }


    public Long getIDemp_proj() {
        return IDemp_proj;
    }

    public void setIDemp_proj(Long iDemp_proj) {
        this.IDemp_proj = iDemp_proj;
    }

    public Long getIDproj() {
        return IDproj;
    }

    public void setIDproj(Long iDproj) {
        this.IDproj = iDproj;
    }

    public Long getIDemp() {return IDemp;}

    public void setIDemp(Long iDemp) {
        this.IDemp = iDemp;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }
}
