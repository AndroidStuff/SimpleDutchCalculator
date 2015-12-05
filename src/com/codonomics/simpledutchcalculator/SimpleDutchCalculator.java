package com.codonomics.simpledutchcalculator;

public class SimpleDutchCalculator {
	private int billAmount;
	private int tipPercent;

	public SimpleDutchCalculator(int billAmount, int tipPercent) {
		this.billAmount = billAmount;
		this.tipPercent = tipPercent;
	}

	public int totalBillAmount() {
		return billAmount + (billAmount * tipPercent / 100);
	}

	public int getBillAmount() {
		return billAmount;
	}

	public int getTipPercent() {
		return tipPercent;
	}

}
