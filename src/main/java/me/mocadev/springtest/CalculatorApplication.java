package me.mocadev.springtest;

public class CalculatorApplication {

	public static void main(String[] args) {
		Calculator calculator = new Calculator(0.0);
		calculator.add(4.0);
		System.out.println(calculator.getResult());
		calculator.subtract(2.0);
		System.out.println(calculator.getResult());
		calculator.multiply(2.0);
		System.out.println(calculator.getResult());
		calculator.divide(2.0);
		System.out.println(calculator.getResult());
	}
}
