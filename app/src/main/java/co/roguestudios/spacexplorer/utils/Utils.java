package co.roguestudios.spacexplorer.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class Utils {

    public static String getStandardValue(double value, boolean money, boolean rate, int decimals) {

        String number;
        String suffix = "";
        DecimalFormat df;

        if (decimals == 1) {
            df = new DecimalFormat("#.#");
        } else if (decimals == 2) {
            df = new DecimalFormat("#.##");
        } else {
            df = new DecimalFormat("#.###");
        }

        df.setRoundingMode(RoundingMode.HALF_EVEN);

        double calcValue = value;
        int i = 0;
        boolean base = false;

        while (!base) {
            if(calcValue == 0) {
                i = - 1;
                base = true;
            }
            calcValue = calcValue / 1000;
            if (1 > calcValue && calcValue > -1) {
                base = true;
            } else {
                i += 1;
            }
        }

        number = df.format(value / Math.pow(10, (3 * i)));

        switch (i) {
            case -1:
                suffix = "";
                break;
            case 0:
                suffix = "k";
                break;
            case 1:
                suffix = "M";
                break;
            case 2:
                suffix = "B";
                break;
            case 3:
                suffix = "T";
                break;
            case 4:
                suffix = "Qd";
                break;
            case 5:
                suffix = "Qn";
                break;
            case 6:
                suffix = "Sx";
                break;
            case 7:
                suffix = "Sp";
                break;
            case 8:
                suffix = "O";
                break;
            case 9:
                suffix = "N";
                break;
        }

        String output = "";

        if (value < 0) {
            output += "-";
            number.replace("-", "");
        }
        if (money) {
            output += "$";
        }
        output += number;
        output += (suffix);
        if (rate) {
            output += "/s";
        }

        return output;
    }

}
