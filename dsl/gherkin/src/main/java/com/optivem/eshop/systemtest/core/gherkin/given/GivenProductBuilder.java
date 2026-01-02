package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenProductBuilder extends BaseGivenBuilder {
    private String sku;
    private String unitPrice;

    public GivenProductBuilder(GivenClause givenClause) {
        super(givenClause);
        withSku(DEFAULT_SKU);
        withUnitPrice(DEFAULT_UNIT_PRICE);
    }

    public GivenProductBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public GivenProductBuilder withUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public GivenProductBuilder withUnitPrice(double unitPrice) {
        withUnitPrice(String.valueOf(unitPrice));
        return this;
    }

    void execute(SystemDsl app) {
        app.erp().returnsProduct()
                .sku(sku)
                .unitPrice(unitPrice)
                .execute()
                .shouldSucceed();
    }
}
