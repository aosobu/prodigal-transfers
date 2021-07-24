package Complaint.utilities;

import org.apache.commons.lang.StringUtils;

public class StringUtilities {

    public static String removeLeadingZeros(String str){
        if(!StringUtils.isEmpty(str)){
            String regex = "^0+(?!$)";
            str = str.replaceAll(regex, "");
        }
        return str;
    }
}
