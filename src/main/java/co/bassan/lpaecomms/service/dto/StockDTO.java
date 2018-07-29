package co.bassan.lpaecomms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Stock entity.
 */
public class StockDTO implements Serializable {

    private Long id;

    @NotNull
    private String stockID;

    @NotNull
    private String stockName;

    @NotNull
    private String stockDesc;

    @NotNull
    private String stockOnHand;

    @NotNull
    private Float stockPrice;

    @NotNull
    private Boolean stockStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockDesc() {
        return stockDesc;
    }

    public void setStockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
    }

    public String getStockOnHand() {
        return stockOnHand;
    }

    public void setStockOnHand(String stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public Float getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Float stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Boolean isStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Boolean stockStatus) {
        this.stockStatus = stockStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockDTO stockDTO = (StockDTO) o;
        if (stockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockDTO{" +
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
