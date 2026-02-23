package com.amaczynski.commission;

import java.math.BigDecimal;

/**
 * Defines a single bracket in a tiered commission structure.
 *
 * @param from lower bound of the tier (inclusive)
 * @param to   upper bound of the tier (exclusive); {@code null} means unbounded
 * @param rate commission rate as a decimal ratio (e.g. 0.05 for 5%)
 */
public record CommissionTier(BigDecimal from, BigDecimal to, BigDecimal rate) {
}
