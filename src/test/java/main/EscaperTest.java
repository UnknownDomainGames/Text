package main;

import nullengine.text.escape.Escaper;
import org.junit.Assert;
import org.junit.Test;

public class EscaperTest {

    @Test
    public void test(){

        Escaper escaper = Escaper.builder()
                .addEscape('{')
                .addEscape('}')
                .build();

        String testString = "asdfh\\g{oa}sh}s\\lk{}}we\\nrklwnt";

        Assert.assertEquals(testString,escaper.unescape(escaper.escape(testString)));

        testString = "abcdf\\{";

        Assert.assertEquals(escaper.indexOfEscapedChar(testString,'{'),6);

        testString = "abcdf\\\\{";
        Assert.assertEquals(escaper.indexOf(testString,'{'),7);

    }

}
