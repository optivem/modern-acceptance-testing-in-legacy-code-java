package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

public abstract class BaseGivenBuilder {
    private final GivenClause givenClause;

    public BaseGivenBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;
    }

    public GivenClause and() {
        return givenClause;
    }

    public WhenClause when() {
        return givenClause.when();
    }

    abstract void execute(SystemDsl app);
}
