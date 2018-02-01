package co.matisses.web.bcs.client;

import co.matisses.web.bcs.employeesinfo.EmployeeInfo;
import co.matisses.web.dto.GenericRESTResponseDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class EmployeesInfoClient {

    private WebTarget webTarget;
    private Client client;

    public EmployeesInfoClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("employeesinfo");
    }

    public EmployeesInfoClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public EmployeeInfo buscarEmpleado(Integer empID) {
        return webTarget.path("find").path(empID.toString()).request(MediaType.APPLICATION_JSON).get(EmployeeInfo.class);
    }

    public GenericRESTResponseDTO editarEmpleado(EmployeeInfo dto) {
        return webTarget.path("edit").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public GenericRESTResponseDTO crearEmpleado(EmployeeInfo dto) {
        return webTarget.path("create").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }
}
