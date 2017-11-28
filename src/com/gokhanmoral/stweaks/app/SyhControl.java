package com.gokhanmoral.stweaks.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

abstract class SyhControl {
    final SyhValueChangedInterface vci; //interface to inform main activity about changed values
    final Context context;
    private final String syh_command = "/res/uci.sh ";
    public String description = "";
    public String name = "";
    public String action = "";
    public View view;
    String valueFromScript = "0";    //loaded from the kernel script (integer, float, "on"/"off"...)
    String valueFromUser = "0";        //user input to be applied to the kernel script (integer, float, "on"/"off"...)
    Boolean canGetValueFromScript = true;
    LinearLayout controlLayout;

    SyhControl(Activity activityIn) {
        context = activityIn;
        vci = (SyhValueChangedInterface) activityIn;
    }

    public boolean isChanged() {
        boolean changed = !valueFromUser.equals(valueFromScript);
        return changed;
    }

    // apply user selected value to the kernel script
    public String setValueViaScript() {
        String command = syh_command + action + " " + valueFromUser;
        String response = Utils.executeRootCommandInThread(command);
        if (response == null) response = "";
        valueFromScript = valueFromUser;
        return response;
    }

    // get the value from kernel script - user interface NOT CHANGED!
    public boolean getValueViaScript(boolean optimized) {
        boolean isOk = false;

        if (this.canGetValueFromScript) {
            String command;
            if (optimized) {
                command = "`/sbin/bb/echo " + action + "|/sbin/bb/awk '{print \". /res/customconfig/actions/\" $1,$1,$2,$3,$4,$5,$6,$7,$8}'`";
            } else {
                command = syh_command + action;
            }
            String response = Utils.executeRootCommandInThread(command);
            if (response != null) {
                if (!response.isEmpty()) {
                    valueFromScript = response.replaceAll("[\n\r]", "");
                    isOk = true;
                }
            }

            if (!isOk) {
                valueFromScript = this.getDefaultValue();
                if (valueFromScript == null) {
                    valueFromScript = "";
                }
            }

            Log.i("getValueViaScript " + this.getClass().getName() + "[" + this.name + "]:", "Value from script:" + valueFromScript);
        }

        return isOk;
    }

    public void create() {
        //Assumptions:
        //1. valueFromScript is set correctly before creation.

/*		
 * TODO: Later concern!
		If we use fragments which can be put to stack then we have problems.
		Because of two conditions we are here:
		1.) Control is created for the first time
		2.) Fragment is paused and resuming...
		Question: Which value should be displayed in the user interface:
		          valueFromScript or valueFromUser?
*/

        valueFromUser = valueFromScript; //prevent value changed event!!!

        controlLayout = new LinearLayout(context);
        controlLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.template_control_layout, controlLayout, false);

        //Control Name
        TextView nameTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.template_textname, controlLayout, false);
        nameTextView.setText(name.toUpperCase(Locale.US));
        controlLayout.addView(nameTextView);

        //Control description
        TextView descriptionTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.template_textdesc, controlLayout, false);
        descriptionTextView.setText(description);
        controlLayout.addView(descriptionTextView);

        createInternal();

        //Panel Separator
        TextView panelSeparatorTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.template_panel_separator, controlLayout, false);
        descriptionTextView.setText(description);
        controlLayout.addView(panelSeparatorTextView);

        view = controlLayout;
    }

    abstract protected void createInternal();    //sets the view

    abstract protected void applyScriptValueToUserInterface();    //clear user input, set it back to the script value

    abstract protected String getDefaultValue();

}
