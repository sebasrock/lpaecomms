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
 * A Stock.
 */
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "stock_id", nullable = false)
    private String stockID;

    @NotNull
    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @NotNull
    @Column(name = "stock_desc", nullable = false)
    private String stockDesc;

    @NotNull
    @Column(name = "stock_on_hand", nullable = false)
    private String stockOnHand;

    @NotNull
    @Column(name = "stock_price", nullable = false)
    private Float stockPrice;

    @NotNull
    @Column(name = "stock_status", nullable = false)
    private Boolean stockStatus;

    @OneToMany(mappedBy = "stock")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceItems> stockIDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockID() {
        return stockID;
    }

    public Stock stockID(String stockID) {
        this.stockID = stockID;
        return this;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public String getStockName() {
        return stockName;
    }

    public Stock stockName(String stockName) {
        this.stockName = stockName;
        return this;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockDesc() {
        return stockDesc;
    }

    public Stock stockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
        return this;
    }

    public void setStockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
    }

    public String getStockOnHand() {
        return stockOnHand;
    }

    public Stock stockOnHand(String stockOnHand) {
        this.stockOnHand = stockOnHand;
        return this;
    }

    public void setStockOnHand(String stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public Float getStockPrice() {
        return stockPrice;
    }

    public Stock stockPrice(Float stockPrice) {
        this.stockPrice = stockPrice;
        return this;
    }

    public void setStockPrice(Float stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Boolean isStockStatus() {
        return stockStatus;
    }

    public Stock stockStatus(Boolean stockStatus) {
        this.stockStatus = stockStatus;
        return this;
    }

    public void setStockStatus(Boolean stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Set<InvoiceItems> getStockIDS() {
        return stockIDS;
    }

    public Stock stockIDS(Set<InvoiceItems> invoiceItems) {
        this.stockIDS = invoiceItems;
        return this;
    }

    public Stock addStockID(InvoiceItems invoiceItems) {
        this.stockIDS.add(invoiceItems);
        invoiceItems.setStock(this);
        return this;
    }

    public Stock removeStockID(InvoiceItems invoiceItems) {
        this.stockIDS.remove(invoiceItems);
        invoiceItems.setStock(null);
        return this;
    }

    public void setStockIDS(Set<InvoiceItems> invoiceItems) {
        this.stockIDS = invoiceItems;
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
        Stock stock = (Stock) o;
        if (stock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", stockID='" + getStockID() + "'" +
            ", stockName='" + getStockName() + "'" +
            ", stockDesc='" + getStockDesc() + "'" +
            ", stockOnHand='" + getStockOnHand() + "'" +
            ", stockPrice=" + getStockPrice() +
            ", stockStatus='" + isStockStatus() + "'" +
            "}";
    }
}
