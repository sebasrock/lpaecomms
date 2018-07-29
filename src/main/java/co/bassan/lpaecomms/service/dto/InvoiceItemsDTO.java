package co.bassan.lpaecomms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InvoiceItems entity.
 */
public class InvoiceItemsDTO implements Serializable {

    private Long id;

    @NotNull
    private String invitemNo;

    @NotNull
    private String invitemQty;

    private Long stockId;

    private Long invoicesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitemNo() {
        return invitemNo;
    }

    public void setInvitemNo(String invitemNo) {
        this.invitemNo = invitemNo;
    }

    public String getInvitemQty() {
        return invitemQty;
    }

    public void setInvitemQty(String invitemQty) {
        this.invitemQty = invitemQty;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getInvoicesId() {
        return invoicesId;
    }

    public void setInvoicesId(Long invoicesId) {
        this.invoicesId = invoicesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoiceItemsDTO invoiceItemsDTO = (InvoiceItemsDTO) o;
        if (invoiceItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceItemsDTO{" +
            "id=" + getId() +
            ", invitemNo='" + getInvitemNo() + "'" +
            ", invitemQty='" + getInvitemQty() + "'" +
            ", stock=" + getStockId() +
            ", invoices=" + getInvoicesId() +
            "}";
    }
}
