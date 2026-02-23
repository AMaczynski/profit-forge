package com.amaczynski.commission;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FlatRateCommissionCalculator implements CommissionCalculator {

    private final BigDecimal rate;
    private final BigDecimal cap;

    /**
     * @param rate decimal ratio, e.g. {@code 0.05} for 5%
     * @param cap  maximum commission amount; {@code null} means no cap
     */
    public FlatRateCommissionCalculator(BigDecimal rate, BigDecimal cap) {
        this.rate = rate;
        this.cap = cap;
    }

    @Override
    public BigDecimal calculate(BigDecimal saleAmount) {
        BigDecimal commission = saleAmount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        if (cap != null && commission.compareTo(cap) > 0) {
            return cap;
        }
        return commission;
    }
}
