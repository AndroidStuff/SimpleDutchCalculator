package com.codonomics.simpledutchcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SimpleDutchCalculatorActivity extends Activity {

	private static final String BILL_AMOUNT = "bill_amount";
	private static final String TIP_PERCENT = "tip_percent";
	private static final String HEAD_COUNT = "head_count";
	private SimpleDutchCalculator data;

	private OnSeekBarChangeListener tipSelectorListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			// data = new SimpleDutchCalculator( data.getBillAmount(), ,
			// data.getHeadcount() );
			data.setTipPercent(seekBar.getProgress()); // Making object MUTABLE
			// to prevent
			// OutOfMemoryError
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
				int billAmount = Integer.parseInt(s.toString());
				data.setBillAmount(billAmount);
				updateTotalBillAmount();
			} catch (NumberFormatException e) {}
		}

		private void uiHeadcount_onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				int headCount = Integer.parseInt(s.toString());
				data.setHeadcount(headCount);
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
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(BILL_AMOUNT, data.getBillAmount());
		outState.putInt(TIP_PERCENT, data.getTipPercent());
		outState.putInt(HEAD_COUNT, data.getHeadcount());
		data = null;
	}

	private void initialize(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			Log.i(APP_OPS_SERVICE, "App just started..");
			uiTipValueSelector().setOnSeekBarChangeListener(tipSelectorListener);
			uiBillAmount().addTextChangedListener(textWatcher);
			uiHeadcount().addTextChangedListener(textWatcher);
			data = new SimpleDutchCalculator(0, 0, 1);
			updateUI();
		} else {
			Log.i(APP_OPS_SERVICE, "App restored from memory..");
			restoreState(savedInstanceState);
			updateUI();
		}
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
		data = new SimpleDutchCalculator(bundle.getInt(BILL_AMOUNT), bundle.getInt(TIP_PERCENT),
				bundle.getInt(HEAD_COUNT));
	}

	private void updateBillAmount() {
		uiBillAmount().setText(Integer.toString(data.getBillAmount()));
	}

	private void updateTotalBillAmount() {
		uiTotalBillValue().setText(Integer.toString(data.totalBillAmount()));
	}

	private void updateCostPerHead() {
		uiHeadcount().setText(Integer.toString(data.getHeadcount())); //TODO: OOM Error vanishes if you comment this line
		uiCostPerHead().setText(Integer.toString(data.costPerHead()));
	}

	private void updateTipPercent() {
		uiTipPercentageChosen().setText(Integer.toString(data.getTipPercent()));
	}

}
