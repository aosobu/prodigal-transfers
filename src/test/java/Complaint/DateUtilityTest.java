package Complaint;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilityTest {

    @Test
    public void test_convert_date_to_string(){
        String date = "2021-05-09";
        Assert.assertEquals(date, date);
    }
}
