package tengxt;

import org.junit.Test;
import tengxt.util.CrowdUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestString {

    @Test
    public void test01(){
        String resource = "123123";
        String md5Str = CrowdUtil.md5(resource);
        System.out.println(md5Str); // 4297F44B13955235245B2497399D7A93
    }


    @Test
    public void test02() {
        Date date = new Date();
        Date dateOfMonth = getFirstDayDateOfMonth(date);
        // 获取当前时间
        String day = new SimpleDateFormat("yyyyMMdd").format(date);
        // 获取传入时间的当月第一天
        String dayofFirst = new SimpleDateFormat("yyyyMMdd").format(dateOfMonth);
        System.out.println(day);
        System.out.println(dayofFirst);

    }

    /**
     * 获取传入日期所在月的第一天
     *
     * @param date
     * @return
     */
    private static Date getFirstDayDateOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }
}
