package co.bassan.lpaecomms.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Invoices entity.
 */
public class InvoicesDTO implements Serializable {

    private Long id;

    @NotNull
    private String invNo;

    @NotNull
    private Instant invDate;

    @NotNull
    private Float invAmount;

    private Boolean invStatus;

    private Long clientsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public Instant getInvDate() {
        return invDate;
    }

    public void setInvDate(Instant invDate) {
        this.invDate = invDate;
    }

    public Float getInvAmount() {
        return invAmount;
    }

    public void setInvAmount(Float invAmount) {
        this.invAmount = invAmount;
    }

    public Boolean isInvStatus() {
        return invStatus;
    }

    public void setInvStatus(Boolean invStatus) {
        this.invStatus = invStatus;
    }

    public Long getClientsId() {
        return clientsId;
    }

    public void setClientsId(Long clientsId) {
        this.clientsId = clientsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoicesDTO invoicesDTO = (InvoicesDTO) o;
        if (invoicesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoicesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoicesDTO{" +
            "id=" + getId() +
            ", invNo='" + getInvNo() + "'" +
            ", invDate='" + getInvDate() + "'" +
            ", invAmount=" + getInvAmount() +
            ", invStatus='" + isInvStatus() + "'" +
            ", clients=" + getClientsId() +
            "}";
    }
}
