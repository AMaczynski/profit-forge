package com.amaczynski.commission.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * When/Then steps that exercise and assert the calculator held in {@link CommissionWorld}.
 */
public class CommissionCalculationSteps {

    private final CommissionWorld world;

    public CommissionCalculationSteps(CommissionWorld world) {
        this.world = world;
    }

    @When("a sale of {bigdecimal} is made")
    public void aSaleOfIsMade(BigDecimal saleAmount) {
        world.result = world.calculator.calculate(saleAmount);
    }

    @Then("the commission should be {bigdecimal}")
    public void theCommissionShouldBe(BigDecimal expected) {
        assertEquals(0, expected.compareTo(world.result),
                () -> "Expected commission " + expected + " but was " + world.result);
    }
}
