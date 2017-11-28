package com.gokhanmoral.stweaks.app;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

class SyhTab {
    public final List<SyhPane> panes;
    public String name;

    public SyhTab() {
        name = "";
        panes = new ArrayList<>();
    }

    public View getCustomView() {
        return null;
    }
}
