package co.bassan.lpaecomms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A InvoiceItems.
 */
@Entity
@Table(name = "invoice_items")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvoiceItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "invitem_no", nullable = false)
    private String invitemNo;

    @NotNull
    @Column(name = "invitem_qty", nullable = false)
    private String invitemQty;

    @ManyToOne
    @JsonIgnoreProperties("stockIDS")
    private Stock stock;

    @ManyToOne
    @JsonIgnoreProperties("invNos")
    private Invoices invoices;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitemNo() {
        return invitemNo;
    }

    public InvoiceItems invitemNo(String invitemNo) {
        this.invitemNo = invitemNo;
        return this;
    }

    public void setInvitemNo(String invitemNo) {
        this.invitemNo = invitemNo;
    }

    public String getInvitemQty() {
        return invitemQty;
    }

    public InvoiceItems invitemQty(String invitemQty) {
        this.invitemQty = invitemQty;
        return this;
    }

    public void setInvitemQty(String invitemQty) {
        this.invitemQty = invitemQty;
    }

    public Stock getStock() {
        return stock;
    }

    public InvoiceItems stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Invoices getInvoices() {
        return invoices;
    }

    public InvoiceItems invoices(Invoices invoices) {
        this.invoices = invoices;
        return this;
    }

    public void setInvoices(Invoices invoices) {
        this.invoices = invoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceItems invoiceItems = (InvoiceItems) o;
        if (invoiceItems.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceItems.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceItems{" +
            "id=" + getId() +
            ", invitemNo='" + getInvitemNo() + "'" +
            ", invitemQty='" + getInvitemQty() + "'" +
            "}";
    }
}
