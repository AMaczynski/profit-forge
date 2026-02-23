package com.amaczynski.commission;

import java.math.BigDecimal;

public interface CommissionCalculator {

    BigDecimal calculate(BigDecimal saleAmount);
}
