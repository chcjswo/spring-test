package me.mocadev.springtest;

public class Calculator {

  private Double result;

  public Calculator() {
    this.result = 0.0;
  }

  public Calculator(Double result) {
    this.result = result;
  }

  public Calculator add(Double number) {
    this.result += number;
    return this;
  }

  public Calculator subtract(Double number) {
    this.result -= number;
    return this;
  }

  public Calculator multiply(Double number) {
    this.result *= number;
    return this;
  }

  public Calculator divide(Double number) {
    if (number == 0) {
      throw new ZeroDivisionException();
    }
    this.result /= number;
    return this;
  }

  public Double getResult() {
    return this.result;
  }

  public static class ZeroDivisionException extends RuntimeException {}
}
