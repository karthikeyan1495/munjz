package com.munjzservice.utility;

import com.munjzservice.MyApp;
import com.munjzservice.R;
import com.munjzservice.summary.model.Values;
import com.munjzservice.tab.active.model.AdditionalService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mac on 11/16/17.
 */

public class StringUtility {

    public static String getFirstLatter(String text) {

        if (text.trim().length() > 1) {
            return text.substring(0, 2).toUpperCase();
        } else {
            return text.trim();
        }
    }

    public static String joinTwoText(String text1, String text2) {
        return capitalize(text1 + " " + text2);
    }

    public static String getDate(String date) {
        if (date != null) {
            String array[] = date.split(" ");
            if (array.length != 0) {
                return array[0];
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String additionalService(List<AdditionalService> list) {
        String s = "";
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    s = s + list.get(i).getService_name();
                } else {
                    s = s + list.get(i).getService_name() + ", ";
                }
            }
        }
        return s;
    }

    public static String additionalServiceName(List<AdditionalService> list) {
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                s = s + list.get(i).getService_name() + ":";
            } else {
                s = s + list.get(i).getService_name() + ":\n";
            }
        }
        return s;
    }

    public static String additionalServicePrice(List<AdditionalService> list) {
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                s = s + list.get(i).getPrice();
            } else {
                s = s + list.get(i).getPrice() + "\n";
            }
        }
        return s;
    }

    public static String getTime(String date) {
        if (date != null) {
            String array[] = date.split(" ");
            if (array.length != 0) {
                return array[1];
            } else {
                return "";
            }
        } else {
            return "";
        }
    }


    public static String removeString(String text) {
        return text.replaceAll("\\D.\\D", "");
    }

    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }
        return capMatcher.appendTail(capBuffer).toString();
    }

    public static boolean isRequestStatus(String status) {
        if (status != null && status.trim().length() != 0 && status.trim().toLowerCase().equals("0")) {
            return true;
        }
        return false;
    }

    public static boolean isNumber(String value) {
        try {
            int number = Integer.valueOf(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String totalPrice(Values values) {
        if (isNumber(values.getValue())) {
            try {
                float totalcount = Float.valueOf(values.getCount());
                float price = Float.valueOf(values.getPrice());
                float count = totalcount * price;
                return MyApp.getContext().getString(R.string.sar) + "." + count;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String countValue(Values values) {
        if (isNumber(values.getValue())) {
            return values.getCount();
        } else {
            return values.getValue();
        }
    }
}
