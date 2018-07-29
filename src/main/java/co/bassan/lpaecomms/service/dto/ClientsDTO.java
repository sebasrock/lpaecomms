package co.bassan.lpaecomms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Clients entity.
 */
public class ClientsDTO implements Serializable {

    private Long id;

    @NotNull
    private String clientID;

    @NotNull
    private String clientFirstName;

    @NotNull
    private String clientLastName;

    @NotNull
    private String clientAddress;

    @NotNull
    private String clientPhone;

    private Boolean clientStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Boolean isClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(Boolean clientStatus) {
        this.clientStatus = clientStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientsDTO clientsDTO = (ClientsDTO) o;
        if (clientsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientsDTO{" +
            "id=" + getId() +
            ", clientID='" + getClientID() + "'" +
            ", clientFirstName='" + getClientFirstName() + "'" +
            ", clientLastName='" + getClientLastName() + "'" +
            ", clientAddress='" + getClientAddress() + "'" +
            ", clientPhone='" + getClientPhone() + "'" +
            ", clientStatus='" + isClientStatus() + "'" +
            "}";
    }
}
