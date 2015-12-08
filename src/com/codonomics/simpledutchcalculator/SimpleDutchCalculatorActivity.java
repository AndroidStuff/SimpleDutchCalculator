package com.codonomics.simpledutchcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SimpleDutchCalculatorActivity extends Activity {

	private static final String BILL_AMOUNT = "bill_amount";
	private static final String TIP_PERCENT = "tip_percent";
	private static final String HEAD_COUNT = "head_count";

	private int billAmount=0;
	private int tipPercent=0;
	private int headcount=1;

	private OnSeekBarChangeListener tipSelectorListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) { }

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) { }

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			tipPercent = seekBar.getProgress();
			updateTipPercent();
			updateTotalBillAmount();
			updateCostPerHead();
		}
	};

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (uiBillAmount().getText().hashCode() == s.hashCode()) {
				uiBillAmount_onTextChanged(s, start, before, count);
			} else if (uiHeadcount().getText().hashCode() == s.hashCode()) {
				uiHeadcount_onTextChanged(s, start, before, count);
			}
		}

		private void uiBillAmount_onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				billAmount = Integer.parseInt(s.toString());
				updateTotalBillAmount();
			} catch (NumberFormatException e) {}
		}

		private void uiHeadcount_onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				headcount = Integer.parseInt(s.toString());
				updateCostPerHead();
			} catch (NumberFormatException e) {}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

		@Override
		public void afterTextChanged(Editable editable) { }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_dutch_calculator);

		initialize(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		//updateUI();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(BILL_AMOUNT, billAmount);
		outState.putInt(TIP_PERCENT, tipPercent);
		outState.putInt(HEAD_COUNT, headcount);
	}

	private void initialize(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			uiTipValueSelector().setOnSeekBarChangeListener(tipSelectorListener);
			uiBillAmount().addTextChangedListener(textWatcher);
			uiHeadcount().addTextChangedListener(textWatcher);
			reset(0, 0, 1);
			return;
		}
		restoreState(savedInstanceState);
	}

	private TextView uiTipPercentageChosen() {
		return (TextView) findViewById(R.id.txtTipPercentageChosen);
	}

	private SeekBar uiTipValueSelector() {
		return (SeekBar) findViewById(R.id.tipPercentageSelector);
	}

	private EditText uiBillAmount() {
		return (EditText) findViewById(R.id.billAmount);
	}

	private EditText uiHeadcount() {
		return (EditText) findViewById(R.id.numberOfPersons);
	}

	private EditText uiTotalBillValue() {
		return (EditText) findViewById(R.id.totalBillAmount);
	}

	private EditText uiCostPerHead() {
		return (EditText) findViewById(R.id.costPerPerson);
	}

	private void updateUI() {
		updateBillAmount();
		updateTipPercent();
		updateTotalBillAmount();
		updateCostPerHead();
	}

	private void restoreState(Bundle bundle) {
		reset(bundle.getInt(BILL_AMOUNT), bundle.getInt(TIP_PERCENT), bundle.getInt(HEAD_COUNT));
	}

	private void updateBillAmount() {
		uiBillAmount().setText(Integer.toString(billAmount));
	}

	private void updateTotalBillAmount() {
		uiTotalBillValue().setText(Integer.toString(totalBillAmount()));
	}

	private void updateCostPerHead() {
		uiCostPerHead().setText(Integer.toString(costPerHead()));
	}

	private void updateTipPercent() {
		uiTipPercentageChosen().setText(Integer.toString(tipPercent));
	}

	public void reset(int billAmount, int tipPercent, int headcount) {
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
}
