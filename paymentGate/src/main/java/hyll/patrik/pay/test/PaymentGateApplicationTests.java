package hyll.patrik.pay.test;

import hyll.patrik.pay.helpers.SignGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Basic tests
 * @author Patrik
 */
class PaymentGateApplicationTests {

    /**
     * Test whether sign is properly generated
     * @author Patrik
     */
    @Test
    public void signCreation() {
        String testMessage = "DemoOMED" + "1.00" + "EUR" + "1234567890" + "Jožko" + "Mrkvička" + "2014-12-01 13:00:00";
        String mid = "DemoOMED";
        String testIv = SignGenerator.reverseString(mid);
        String testKey = "1234567812345678123456781234567812345678123456781234567812345678";
        String sign = "2b817107edb88129d9aa8316f8758270";
        Assertions.assertEquals(SignGenerator.generateSign(testMessage, testKey, testIv), sign);

    }



}
