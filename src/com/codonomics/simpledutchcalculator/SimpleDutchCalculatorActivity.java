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
	private SimpleDutchCalculator data = new SimpleDutchCalculator(0,0,1);

	private OnSeekBarChangeListener tipSelectorListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {  }

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) { }

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			//data = new SimpleDutchCalculator( data.getBillAmount(), , data.getHeadcount() );
			data.setTipPercent(seekBar.getProgress()); //Making object MUTABLE to prevent OutOfMemoryError
			updateTipPercent();
			updateTotalBillAmount();
			updateCostPerHead();
		}
	};

	private TextWatcher billAmountWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				int billAmount = Integer.parseInt(s.toString());
				//data = new SimpleDutchCalculator( billAmount,  data.getTipPercent(), data.getHeadcount() );
				data.setBillAmount(billAmount); //Making object MUTABLE to prevent OutOfMemoryError
				updateTotalBillAmount();
			} catch (NumberFormatException e) {
				//				updateBillAmount();
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	private TextWatcher headCountWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				int headCount = Integer.parseInt(s.toString());
				//data = new SimpleDutchCalculator( data.getBillAmount(),  data.getTipPercent(), headCount );
				data.setHeadcount(headCount); //Making object MUTABLE to prevent OutOfMemoryError
				updateCostPerHead();
			} catch (NumberFormatException e) {
				//				updateCostPerHead();
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
	private EditText uiHeadcount;
	private EditText uiCostPerHead;

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
	}

	private void initialize(Bundle savedInstanceState) {
		initUIReferences();
		if(savedInstanceState == null) {
			Log.i(APP_OPS_SERVICE, "App just started..");
			uiTipValueSelector.setOnSeekBarChangeListener(tipSelectorListener);
			uiBillAmount.addTextChangedListener(billAmountWatcher);
			uiHeadcount.addTextChangedListener(headCountWatcher);
			updateUI();
		} else {
			Log.i(APP_OPS_SERVICE, "App restored from memory..");
			restoreState(savedInstanceState);
			updateUI();
		}
	}

	private void initUIReferences() {
		uiTipPercentageChosen = (TextView) findViewById(R.id.txtTipPercentageChosen);
		uiTipValueSelector = (SeekBar) findViewById(R.id.tipPercentageSelector);
		uiBillAmount = (EditText) findViewById(R.id.billAmount);
		uiTotalBillValue = (EditText) findViewById(R.id.totalBillAmount);
		uiHeadcount = (EditText) findViewById(R.id.numberOfPersons);
		uiCostPerHead = (EditText) findViewById(R.id.costPerPerson);
	}

	private void updateUI() {
		updateBillAmount();
		updateTipPercent();
		updateTotalBillAmount();
		updateCostPerHead();
	}

	private void restoreState(Bundle bundle) {
		//data = new SimpleDutchCalculator( bundle.getInt(BILL_AMOUNT), bundle.getInt(TIP_AMOUNT), data.getHeadcount() );
		data.setBillAmount(bundle.getInt(BILL_AMOUNT)); //Making object MUTABLE to prevent OutOfMemoryError
		data.setTipPercent(bundle.getInt(TIP_PERCENT)); //Making object MUTABLE to prevent OutOfMemoryError
		data.setHeadcount(bundle.getInt(HEAD_COUNT)); //Making object MUTABLE to prevent OutOfMemoryError
	}

	private void updateBillAmount() {
		uiBillAmount.setText("" +
				data.getBillAmount());
	}

	private void updateTotalBillAmount() {
		uiTotalBillValue.setText("" + data.totalBillAmount());
	}

	private void updateCostPerHead() {
		uiHeadcount.setText("" + data.getHeadcount());
		uiCostPerHead.setText("" + data.costPerHead());
		Log.i("Cost Per Head", data.costPerHead() + "");
	}

	private void updateTipPercent() {
		uiTipPercentageChosen.setText("" + data.getTipPercent());
	}

}
