package com.amaczynski.commission.steps;

import com.amaczynski.commission.CommissionTier;
import com.amaczynski.commission.FlatRateCommissionCalculator;
import com.amaczynski.commission.TieredCommissionCalculator;
import io.cucumber.java.en.Given;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Given steps that configure and inject a {@link com.amaczynski.commission.CommissionCalculator}
 * into the shared {@link CommissionWorld}.
 *
 * PicoContainer creates a single {@link CommissionWorld} per scenario and supplies it here
 * and to every other step definition class that declares the same constructor parameter.
 */
public class CommissionConfigurationSteps {

    private final CommissionWorld world;

    public CommissionConfigurationSteps(CommissionWorld world) {
        this.world = world;
    }

    @Given("a flat commission rate of {bigdecimal}%")
    public void aFlatCommissionRateOf(BigDecimal ratePercent) {
        world.pendingRate = ratePercent.divide(BigDecimal.valueOf(100));
        world.calculator = new FlatRateCommissionCalculator(world.pendingRate, null);
    }

    @Given("a maximum commission cap of {bigdecimal}")
    public void aMaximumCommissionCapOf(BigDecimal cap) {
        world.calculator = new FlatRateCommissionCalculator(world.pendingRate, cap);
    }

    @Given("a tiered commission structure:")
    public void aTieredCommissionStructure(List<Map<String, String>> rows) {
        List<CommissionTier> tiers = rows.stream()
                .map(row -> new CommissionTier(
                        new BigDecimal(row.get("tierFrom")),
                        row.get("tierTo").isBlank() ? null : new BigDecimal(row.get("tierTo")),
                        new BigDecimal(row.get("rate")).divide(BigDecimal.valueOf(100))
                ))
                .collect(Collectors.toList());
        world.calculator = new TieredCommissionCalculator(tiers);
    }
}
