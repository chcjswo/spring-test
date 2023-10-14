package me.mocadev.springtest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {

  @Test
  void add() {
    Calculator calculator = new Calculator();
    calculator.add(10.0);

    assertThat(calculator.getResult()).isEqualTo(10.0);
  }

  @Test
  void subtract() {
    Calculator calculator = new Calculator(10.0);
    calculator.subtract(5.0);

    assertThat(calculator.getResult()).isEqualTo(5.0);
  }

  @Test
  void multiply() {
    Calculator calculator = new Calculator(10.0);
    calculator.multiply(2.0);

    assertThat(calculator.getResult()).isEqualTo(20.0);
  }

  @Test
  void divide() {
    Calculator calculator = new Calculator(10.0);
    calculator.divide(2.0);

    assertThat(calculator.getResult()).isEqualTo(5.0);
  }
}
