package com.strv.codemanager.utility;


import java.awt.*;

public class DimensionManager {
    public static int fontSize(int size) {
        double screen = Toolkit.getDefaultToolkit().getScreenResolution();
        return (int) (size * (screen / 96f));
    }
}
