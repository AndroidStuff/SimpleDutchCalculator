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
	private static final String TIP_AMOUNT = "tip_amount";
	private SimpleDutchCalculator data = new SimpleDutchCalculator(0,0);

	private OnSeekBarChangeListener tipSelectorListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {  }

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) { }

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			data = new SimpleDutchCalculator( data.getBillAmount(), seekBar.getProgress() );
			updateTipPercent();
			updateTotalBillAmount();
		}
	};

	private TextWatcher billAmountWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				int billAmount = Integer.parseInt(s.toString());
				data = new SimpleDutchCalculator( billAmount,  data.getTipPercent());
				updateTotalBillAmount();
			} catch (NumberFormatException e) {
				updateBillAmount();
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	private EditText uiTotalBillValue;
	private EditText uiBillAmount;
	private TextView uiTipPercentageChosen;
	private SeekBar uiTipValueSelector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_dutch_calculator);

		initialize(savedInstanceState);
	}

	private void initialize(Bundle savedInstanceState) {
		initUIReferences();
		if(savedInstanceState == null) {
			Log.i(APP_OPS_SERVICE, "App just started..");
			uiTipValueSelector.setOnSeekBarChangeListener(tipSelectorListener);
			uiBillAmount.addTextChangedListener(billAmountWatcher);
			updateUI();
		} else {
			Log.i(APP_OPS_SERVICE, "App restored from memory..");
			restoreState(savedInstanceState);
		}
	}

	private void initUIReferences() {
		uiTipPercentageChosen = (TextView) findViewById(R.id.txtTipPercentageChosen);
		uiTipValueSelector = (SeekBar) findViewById(R.id.tipPercentageSelector);
		uiBillAmount = (EditText) findViewById(R.id.billAmount);
		uiTotalBillValue = (EditText) findViewById(R.id.totalBillAmount);
	}

	private void updateUI() {
		updateBillAmount();
		updateTipPercent();
		updateTotalBillAmount();
	}

	private void restoreState(Bundle bundle) {
		data = new SimpleDutchCalculator( bundle.getInt(BILL_AMOUNT), bundle.getInt(TIP_AMOUNT) );
	}

	private void updateBillAmount() {
		uiBillAmount.setText("" +
				data.getBillAmount());
	}

	private void updateTotalBillAmount() {
		uiTotalBillValue.setText("" + data.totalBillAmount());
	}

	private void updateTipPercent() {
		uiTipPercentageChosen.setText("" + data.getTipPercent());
	}

}
