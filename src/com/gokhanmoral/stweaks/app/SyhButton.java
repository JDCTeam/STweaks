package com.gokhanmoral.stweaks.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SyhButton extends SyhControl implements OnClickListener {

    public String label;

    SyhButton(Activity activityIn) {
        super(activityIn);
        canGetValueFromScript = false;
    }

    @Override
    public void onClick(View v) {
        String res = setValueViaScript();
        Toast.makeText(context, res, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void createInternal() {
        valueFromScript = "";
        valueFromUser = "";

        //create Button from xml template
        View temp = LayoutInflater.from(context).inflate(R.layout.template_button, controlLayout, false);
        Button button = (Button) temp.findViewById(R.id.SyhButton);
        button.setText(label);
        button.setOnClickListener(this);
        controlLayout.addView(temp);
    }

    @Override
    protected void applyScriptValueToUserInterface() {
        valueFromUser = valueFromScript;
    }

    @Override
    protected String getDefaultValue() {
        return null;
    }


}
