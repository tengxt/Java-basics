package tengxt;

import org.junit.Test;
import tengxt.util.CrowdUtil;

public class TestString {

    @Test
    public void test01(){
        String resource = "123123";
        String md5Str = CrowdUtil.md5(resource);
        System.out.println(md5Str); // 4297F44B13955235245B2497399D7A93
    }
}
