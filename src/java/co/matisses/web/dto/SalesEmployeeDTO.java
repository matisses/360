package co.matisses.web.dto;

import co.matisses.persistence.sap.entity.SalesPerson;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class SalesEmployeeDTO {

    private Double comision;
    private String id;
    private String name;
    private String slpCode;

    public SalesEmployeeDTO() {
    }

    public SalesEmployeeDTO(String name, String slpCode) {
        this.name = name;
        this.slpCode = slpCode;
    }

    public SalesEmployeeDTO(Double comision, String name, String slpCode) {
        this.comision = comision;
        this.name = name;
        this.slpCode = slpCode;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(String slpCode) {
        this.slpCode = slpCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalesEmployeeDTO other = (SalesEmployeeDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public static List<SalesEmployeeDTO> getOneEmployeeList(String name, String slpCode) {
        List<SalesEmployeeDTO> employee = new ArrayList<>();
        employee.add(new SalesEmployeeDTO(name, slpCode));
        return employee;
    }

    public static List<SalesEmployeeDTO> getOneEmployeeList(SalesPerson entity) {
        List<SalesEmployeeDTO> employee = new ArrayList<>();
        employee.add(new SalesEmployeeDTO(entity.getSlpName(), entity.getSlpCode().toString()));
        return employee;
    }

    @Override
    public String toString() {
        return "SalesEmployeeDTO{" + "id=" + id + ", name=" + name + ", slpCode=" + slpCode + '}';
    }
}
