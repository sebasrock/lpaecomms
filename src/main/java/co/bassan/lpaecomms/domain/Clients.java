package co.bassan.lpaecomms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Clients.
 */
@Entity
@Table(name = "clients")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private String clientID;

    @NotNull
    @Column(name = "client_first_name", nullable = false)
    private String clientFirstName;

    @NotNull
    @Column(name = "client_last_name", nullable = false)
    private String clientLastName;

    @NotNull
    @Column(name = "client_address", nullable = false)
    private String clientAddress;

    @NotNull
    @Column(name = "client_phone", nullable = false)
    private String clientPhone;

    @Column(name = "client_status")
    private Boolean clientStatus;

    @OneToMany(mappedBy = "clients")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoices> clientIDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientID() {
        return clientID;
    }

    public Clients clientID(String clientID) {
        this.clientID = clientID;
        return this;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public Clients clientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
        return this;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public Clients clientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
        return this;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public Clients clientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
        return this;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public Clients clientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
        return this;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Boolean isClientStatus() {
        return clientStatus;
    }

    public Clients clientStatus(Boolean clientStatus) {
        this.clientStatus = clientStatus;
        return this;
    }

    public void setClientStatus(Boolean clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Set<Invoices> getClientIDS() {
        return clientIDS;
    }

    public Clients clientIDS(Set<Invoices> invoices) {
        this.clientIDS = invoices;
        return this;
    }

    public Clients addClientID(Invoices invoices) {
        this.clientIDS.add(invoices);
        invoices.setClients(this);
        return this;
    }

    public Clients removeClientID(Invoices invoices) {
        this.clientIDS.remove(invoices);
        invoices.setClients(null);
        return this;
    }

    public void setClientIDS(Set<Invoices> invoices) {
        this.clientIDS = invoices;
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
        Clients clients = (Clients) o;
        if (clients.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clients.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clients{" +
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
