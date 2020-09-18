package model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class Sale {
    private final UUID idSales;
    private final UUID idGood;
    private final Timestamp saleData;
    private final int saleAmount;

    public Sale(UUID idSales, UUID idGood, Timestamp saleData, int saleAmount) {
        this.idSales = idSales;
        this.idGood = idGood;
        this.saleData = saleData;
        this.saleAmount = saleAmount;
    }

    public UUID getIdSales() {
        return idSales;
    }

    public UUID getIdGood() {
        return idGood;
    }

    public Date getSaleData() {
        return saleData;
    }

    public int getSaleAmount() {
        return saleAmount;
    }
}
