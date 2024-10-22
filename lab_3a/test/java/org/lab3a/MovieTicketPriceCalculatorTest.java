package org.lab3a;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieTicketPriceCalculatorTest {

  // Parameterized test for computePrice
  @ParameterizedTest
  @CsvSource({
          "12:00, 10, 2100",  // Kid pricing
          "18:00, 10, 2400",  // Kid pricing
          "11:59, 10, 2400", // Kid pricing
          "12:00, 70, 2000",  // Senior pricing
          "18:00, 70, 2300",  // Senior pricing
          "11:59, 70, 2300", // Senior pricing
          "12:00, 20, 2400",   // Regular pricing
          "18:00, 20, 2700",   // Regular pricing
          "11:59, 20, 2700"  // Regular pricing
  })
  void testComputePrice(String time, int age, int expectedPrice) {
    // Setup
    final LocalTime startMatineeTime = LocalTime.of(12, 0);
    final LocalTime endMatineeTime = LocalTime.of(17, 0);
    final int maxChildAge = 12;
    final int minSeniorAge = 65;
    final MovieTicketPriceCalculator calc = new MovieTicketPriceCalculator(
            startMatineeTime, endMatineeTime, maxChildAge, minSeniorAge);
    // Exercise
    final LocalTime localTime = LocalTime.parse(time);
    final int price = calc.computePrice(localTime, age);
    // Verify
    assertEquals(expectedPrice, price);
  }

  @Test
  void testInvalidMatineeTime() {
    // Setup invalid start and end time
    final LocalTime startMatineeTime = LocalTime.of(18, 0);
    final LocalTime endMatineeTime = LocalTime.of(17, 0);
    final int maxChildAge = 12;
    final int minSeniorAge = 65;

    // Verify that the constructor throws IllegalArgumentException
    assertThrows(IllegalArgumentException.class, () -> new MovieTicketPriceCalculator(startMatineeTime, endMatineeTime, maxChildAge, minSeniorAge));
  }
}
