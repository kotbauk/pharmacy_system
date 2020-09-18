package model;

import java.util.UUID;

public class GoodsOnWarehouse {
    final private UUID id;
    protected final int amount;
    protected final int minimal_amount;

    public GoodsOnWarehouse(UUID id, int amount, int minimal_amount) {
        this.id = id;
        this.amount = amount;
        this.minimal_amount = minimal_amount;
    }

    public UUID getId() {
        return id;
    }

}
