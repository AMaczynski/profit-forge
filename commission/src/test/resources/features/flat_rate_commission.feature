Feature: Flat Rate Commission Calculation
  As a sales manager
  I want to calculate commissions as a flat percentage of the sale amount
  So that agents receive predictable compensation

  Scenario Outline: Commission is a percentage of the sale amount
    Given a flat commission rate of <rate>%
    When a sale of <amount> is made
    Then the commission should be <commission>

    Examples:
      | rate | amount  | commission |
      | 5    | 1000.00 | 50.00      |
      | 10   | 250.00  | 25.00      |
      | 2.5  | 800.00  | 20.00      |
      | 0    | 1000.00 | 0.00       |

  Scenario: Commission is capped at a configured maximum
    Given a flat commission rate of 10%
    And a maximum commission cap of 200.00
    When a sale of 3000.00 is made
    Then the commission should be 200.00

  Scenario: Commission stays below the cap
    Given a flat commission rate of 10%
    And a maximum commission cap of 200.00
    When a sale of 1500.00 is made
    Then the commission should be 150.00
