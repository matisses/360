package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class SucursalDTO {
    private String code;
    private String name;
    private List<EmpleadoDTO> empleados;

    public SucursalDTO() {
        empleados = new ArrayList<>();
    }

    public SucursalDTO(String code, String name) {
        this.code = code;
        this.name = name;
        empleados = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.code);
        return hash;
    }

    public List<EmpleadoDTO> getEmpleados() {
        return empleados;
    }

    public void addEmpleado(EmpleadoDTO emp) {
        if (emp != null && !empleados.contains(emp)) {
            empleados.add(emp);
            Collections.sort(empleados);
        }
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
        final SucursalDTO other = (SucursalDTO) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SucursalDTO{" + "code=" + code + ", name=" + name + ", empleados=" + empleados + '}';
    }
}
