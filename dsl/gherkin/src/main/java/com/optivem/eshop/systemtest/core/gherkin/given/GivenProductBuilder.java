package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;
import com.optivem.lang.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenProductBuilder extends BaseGivenBuilder {
    private static final Logger log = LoggerFactory.getLogger(GivenProductBuilder.class);
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
        withUnitPrice(Converter.fromDouble(unitPrice));
        return this;
    }

    void execute(SystemDsl app) {
        long start = System.currentTimeMillis();
        app.erp().returnsProduct()
                .sku(sku)
                .unitPrice(unitPrice)
                .execute()
                .shouldSucceed();
        long elapsed = System.currentTimeMillis() - start;
        log.info("[PERF] GivenProductBuilder.execute took {}ms", elapsed);
    }
}
