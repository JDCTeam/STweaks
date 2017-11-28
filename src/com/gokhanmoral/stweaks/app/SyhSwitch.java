package com.gokhanmoral.stweaks.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;

public class SyhSwitch extends SyhControl implements OnClickListener {

    public String label;
    private Switch syhswitch;
    SyhSwitch(Activity activityIn) {
        super(activityIn);
    }

    @Override
    public void createInternal() {

        //Assumption: valueFromScript is set correctly.

        syhswitch = (Switch) LayoutInflater.from(context).inflate(R.layout.template_switch, controlLayout, false);
        syhswitch.setText(label);
        syhswitch.setOnClickListener(this);

        applyScriptValueToUserInterface();

        controlLayout.addView(syhswitch);
    }

    @Override
    public void onClick(View v) {
        //-- This not true >>>  this.valueInput = Boolean.toString(syhswitch.isChecked());
        this.valueFromUser = convertFromControlFormatToScriptFormat(syhswitch.isChecked());
        this.vci.valueChanged();
    }

    @Override
    protected void applyScriptValueToUserInterface() {
        //-- This not true >>> boolean hardware = Boolean.parseBoolean(this.valueHardware);

        if (syhswitch != null) {
            boolean hardware = convertFromScriptFormatToControlFormat(valueFromScript);
            syhswitch.setChecked(hardware);
        }
        valueFromUser = valueFromScript;
    }

    Boolean convertFromScriptFormatToControlFormat(String input) {
        boolean hardware = input.equals("on");
        return hardware;
    }

    String convertFromControlFormatToScriptFormat(Boolean input) {
        String scriptVal = (input) ? ("on") : ("off");
        return scriptVal;
    }


    @Override
    protected String getDefaultValue() {
        return "off";
    }


}
