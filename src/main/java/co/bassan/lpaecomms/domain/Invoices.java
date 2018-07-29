package co.bassan.lpaecomms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "invoices")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invoices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "inv_no", nullable = false)
    private String invNo;

    @NotNull
    @Column(name = "inv_date", nullable = false)
    private Instant invDate;

    @NotNull
    @Column(name = "inv_amount", nullable = false)
    private Float invAmount;

    @Column(name = "inv_status")
    private Boolean invStatus;

    @ManyToOne
    @JsonIgnoreProperties("clientIDS")
    private Clients clients;

    @OneToMany(mappedBy = "invoices")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceItems> invNos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvNo() {
        return invNo;
    }

    public Invoices invNo(String invNo) {
        this.invNo = invNo;
        return this;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public Instant getInvDate() {
        return invDate;
    }

    public Invoices invDate(Instant invDate) {
        this.invDate = invDate;
        return this;
    }

    public void setInvDate(Instant invDate) {
        this.invDate = invDate;
    }

    public Float getInvAmount() {
        return invAmount;
    }

    public Invoices invAmount(Float invAmount) {
        this.invAmount = invAmount;
        return this;
    }

    public void setInvAmount(Float invAmount) {
        this.invAmount = invAmount;
    }

    public Boolean isInvStatus() {
        return invStatus;
    }

    public Invoices invStatus(Boolean invStatus) {
        this.invStatus = invStatus;
        return this;
    }

    public void setInvStatus(Boolean invStatus) {
        this.invStatus = invStatus;
    }

    public Clients getClients() {
        return clients;
    }

    public Invoices clients(Clients clients) {
        this.clients = clients;
        return this;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Set<InvoiceItems> getInvNos() {
        return invNos;
    }

    public Invoices invNos(Set<InvoiceItems> invoiceItems) {
        this.invNos = invoiceItems;
        return this;
    }

    public Invoices addInvNo(InvoiceItems invoiceItems) {
        this.invNos.add(invoiceItems);
        invoiceItems.setInvoices(this);
        return this;
    }

    public Invoices removeInvNo(InvoiceItems invoiceItems) {
        this.invNos.remove(invoiceItems);
        invoiceItems.setInvoices(null);
        return this;
    }

    public void setInvNos(Set<InvoiceItems> invoiceItems) {
        this.invNos = invoiceItems;
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
        Invoices invoices = (Invoices) o;
        if (invoices.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoices.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invoices{" +
            "id=" + getId() +
            ", invNo='" + getInvNo() + "'" +
            ", invDate='" + getInvDate() + "'" +
            ", invAmount=" + getInvAmount() +
            ", invStatus='" + isInvStatus() + "'" +
            "}";
    }
}
