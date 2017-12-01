package com.gokhanmoral.stweaks.app;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public final class SyhSeekBar extends SyhControl implements OnSeekBarChangeListener{

	private static final String LOG_TAG = Utils.class.getName();


	SyhSeekBar(Activity activityIn) {
		super(activityIn);
	}

	private SeekBar seekbar;
	private TextView seekBarValueText;

    public String unit = "";
	public int min= 0;
	public int max = 0;
	public int step = 1;
	public boolean reversed = false;

	
	//TODO: reverse adjustment needed!
	//DONE: secondary progress needed!
	//DONE: Move to XML
	
	@Override
	public void createInternal() {
		
		//Assumption: valueFromScript is set correctly. 

		Integer val = 0;
		try
		{
			val = Integer.parseInt(valueFromScript);
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "SyhSeekBar createInternal: valueFromScript cannot be converted!");
		}
		
		if (val < min)
		{
			val = min;
			valueFromScript = Integer.toString(min);
		}
		else if (val > max)
		{
			val = max;
			valueFromScript = Integer.toString(max);
		}
		valueFromUser = valueFromScript;

        int maxInSteps = (max - min) / step;
		
		//--Log.w(LOG_TAG, " max:" + Integer.toString(max) + " step:" + Integer.toString(step) + " maxInSteps:" + Integer.toString(maxInSteps));

		// Moved to xml
        //seekbar = new SeekBar(context);
		//seekbar.setMax(maxInSteps);
		//seekbar.setProgress( (val-min) /step);
        //seekbar.setPadding(100, 10, 100, 10);
		//seekbar.setOnSeekBarChangeListener(this); // set listener.

        seekbar = (SeekBar) LayoutInflater.from(context).inflate(R.layout.template_seekbar, controlLayout, false);
        seekbar.setMax(maxInSteps);
        seekbar.setProgress((val-min) /step);
        seekbar.setOnSeekBarChangeListener(this); // set listener.



		applyScriptValueToUserInterface();
		
		controlLayout.addView(seekbar);
		
        //DONE: Move this to xml
		//seekBarValueText = new TextView(context);
        //seekBarValueText.setTextColor(Color.BLACK);
        //seekBarValueText.setBackgroundColor(Color.WHITE);
        //seekBarValueText.setText(valueFromUser + " " + unit);
        //seekBarValueText.setGravity(Gravity.CENTER);
		//controlLayout.addView(seekBarValueText);

        seekBarValueText = (TextView) LayoutInflater.from(context).inflate(R.layout.template_seekbar_text, controlLayout, false);
        seekBarValueText.setText(valueFromUser + " " + unit);
        controlLayout.addView(seekBarValueText);

	}
	
	@Override
    public void onProgressChanged(SeekBar seekBar, int progress,
    		boolean fromUser) {
    	
		//-- Log.i(this.getClass().getName(), "min:" + min + " max:" + max + " seekMax:" + seekbar.getMax() + " progress:" + progress);
		int value = min + progress * step;
		valueFromUser = Integer.toString(value);
    	seekBarValueText.setText(valueFromUser + " " + unit);
		//--seekBarValueText.setText(progress + " " + unit);
    }

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
        if (isChanged())
    	{
    		this.vci.valueChanged();
            // Set the secondary progress bar
            Integer  val = Integer.parseInt(valueFromScript);
            seekbar.setSecondaryProgress(((val-min) /step));
    	}
	}

	@Override
	protected void applyScriptValueToUserInterface() {
		if (seekbar != null)
		{
			Integer valueHardwareInt = 0;
			try
			{
				valueHardwareInt =Integer.parseInt(valueFromScript);
			}
			catch (NumberFormatException nfe)
			{
				// Do Nothing
			}
			Integer progress = (valueHardwareInt - min) / step;
			seekbar.setProgress(progress);
		}
		valueFromUser = valueFromScript;
	}

	@Override
	protected String getDefaultValue() {
		return Integer.toString(min);
	}

}
