package com.amaczynski.commission;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TieredCommissionCalculator implements CommissionCalculator {

    private final List<CommissionTier> tiers;

    /**
     * Tiers must be ordered by {@link CommissionTier#from()} ascending.
     * Each tier's range is [from, to) — lower inclusive, upper exclusive.
     * A {@code null} upper bound means the tier extends to the full sale amount.
     */
    public TieredCommissionCalculator(List<CommissionTier> tiers) {
        this.tiers = tiers;
    }

    @Override
    public BigDecimal calculate(BigDecimal saleAmount) {
        BigDecimal total = BigDecimal.ZERO;
        for (CommissionTier tier : tiers) {
            if (saleAmount.compareTo(tier.from()) <= 0) {
                break;
            }
            BigDecimal upperBound = tier.to() != null ? tier.to() : saleAmount;
            BigDecimal applicable = saleAmount.min(upperBound).subtract(tier.from());
            if (applicable.compareTo(BigDecimal.ZERO) > 0) {
                total = total.add(applicable.multiply(tier.rate()));
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
