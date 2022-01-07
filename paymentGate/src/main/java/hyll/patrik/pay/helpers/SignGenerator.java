package hyll.patrik.pay.helpers;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.maven.shared.utils.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Random;

/**
 * Generate secret sign needed for transaction
 * contains helper methods
 *
 * @author 24-pay
 */
public class SignGenerator {
    public static String generateSign(String message, String key, String iv) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            byte[] keyBytes = Hex.decodeHex(key.toCharArray());
            byte[] ivBytes = iv.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            byte[] sha1Hash = DigestUtils.sha1(message);
            byte[] encryptedData = encryptCipher.doFinal(sha1Hash);
            return Hex.encodeHexString(encryptedData).substring(0, 32);
        } catch (Exception e) {
            System.err.println("ERROR! " + e);
            return null;
        }
    }

    /**
     * Concatenate String with it's reversed form
     *
     * @param mid
     * @return String
     * @author Patrik
     */
    public static String reverseString(String mid) {
        return mid + StringUtils.reverse(mid);
    }

    /**
     * Return random alphanumeric String
     *
     * @return String
     * @author https://www.baeldung.com/java-random-string
     */
    public static String randomAlphaNumericString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
