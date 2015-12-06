package com.codonomics.simpledutchcalculator;

public class SimpleDutchCalculator {
	private int billAmount;
	private int tipPercent;
	private int headcount;

	public SimpleDutchCalculator(int billAmount, int tipPercent, int headcount) {
		this.billAmount = billAmount;
		this.tipPercent = tipPercent;
		this.headcount = headcount;
	}

	public int totalBillAmount() {
		return billAmount + (billAmount * tipPercent / 100);
	}

	public int costPerHead() {
		return totalBillAmount() / headcount;
	}

	public int getBillAmount() {
		return billAmount;
	}

	public int getTipPercent() {
		return tipPercent;
	}

	public int getHeadcount() {
		return headcount;
	}

	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}

	public void setTipPercent(int tipPercent) {
		this.tipPercent = tipPercent;
	}

	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}

}
