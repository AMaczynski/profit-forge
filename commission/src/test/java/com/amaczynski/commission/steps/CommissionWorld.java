package com.amaczynski.commission.steps;

import com.amaczynski.commission.CommissionCalculator;

import java.math.BigDecimal;

/**
 * Shared scenario context injected by PicoContainer into every step definition class.
 * One fresh instance is created per scenario.
 */
public class CommissionWorld {

    CommissionCalculator calculator;
    BigDecimal pendingRate;   // retained so the cap step can rebuild the calculator
    BigDecimal result;
}
