Feature: Tiered Commission Calculation
  As a sales manager
  I want higher commission rates to apply at higher sale amounts
  So that agents are incentivized to close larger deals

  Scenario: Sale falls entirely within the first tier
    Given a tiered commission structure:
      | tierFrom | tierTo  | rate |
      | 0.00     | 1000.00 | 3    |
      | 1000.00  | 5000.00 | 5    |
      | 5000.00  |         | 7    |
    When a sale of 800.00 is made
    Then the commission should be 24.00

  Scenario: Sale spans the first and second tiers
    Given a tiered commission structure:
      | tierFrom | tierTo  | rate |
      | 0.00     | 1000.00 | 3    |
      | 1000.00  | 5000.00 | 5    |
      | 5000.00  |         | 7    |
    When a sale of 2000.00 is made
    Then the commission should be 80.00

  Scenario: Sale spans all three tiers
    Given a tiered commission structure:
      | tierFrom | tierTo  | rate |
      | 0.00     | 1000.00 | 3    |
      | 1000.00  | 5000.00 | 5    |
      | 5000.00  |         | 7    |
    When a sale of 6000.00 is made
    Then the commission should be 300.00

  Scenario Outline: Boundary values for each tier
    Given a tiered commission structure:
      | tierFrom | tierTo  | rate |
      | 0.00     | 1000.00 | 3    |
      | 1000.00  | 5000.00 | 5    |
      | 5000.00  |         | 7    |
    When a sale of <amount> is made
    Then the commission should be <commission>

    Examples:
      | amount  | commission |
      | 0.00    | 0.00       |
      | 1000.00 | 30.00      |
      | 5000.00 | 230.00     |
