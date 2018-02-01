package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class LaborEmpleadoCcygaDTO implements Comparable<LaborEmpleadoCcygaDTO> {

    private EmpleadoCcygaDTO empleado;
    private LaborCcygaDTO labor;

    public LaborEmpleadoCcygaDTO() {
    }

    public EmpleadoCcygaDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoCcygaDTO empleado) {
        this.empleado = empleado;
    }

    public LaborCcygaDTO getLabor() {
        return labor;
    }

    public void setLabor(LaborCcygaDTO labor) {
        this.labor = labor;
    }

    @Override
    public int compareTo(LaborEmpleadoCcygaDTO o) {
        return this.getEmpleado().compareTo(o.getEmpleado());
    }
}
