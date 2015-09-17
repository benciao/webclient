package com.ecg.webclient.feature.administration.authentication;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.codec.Hex;

public class PasswordEncoder
{
    static final Logger logger = LogManager.getLogger(PasswordEncoder.class.getName());

    public static String encodeComplex(String simpleEncodedPassword, String rid)
    {
        try
        {
            MessageDigest messageDigestRid = MessageDigest.getInstance("MD5");
            messageDigestRid.reset();
            messageDigestRid.update(rid.getBytes(Charset.forName("UTF8")));
            final byte[] ridResultByte = messageDigestRid.digest();
            final String ridResult = new String(Hex.encode(ridResultByte));

            String finalString = simpleEncodedPassword + ridResult;

            MessageDigest messageDigestFinal = MessageDigest.getInstance("MD5");
            messageDigestFinal.reset();
            messageDigestFinal.update(finalString.getBytes(Charset.forName("UTF8")));
            final byte[] finalResultByte = messageDigestFinal.digest();
            final String finalResult = new String(Hex.encode(finalResultByte));

            return finalResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error(e);
        }

        return null;
    }

    public static String encodeSimple(String valueToEncode)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();
            messageDigest.update(valueToEncode.getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            final String result = new String(Hex.encode(resultByte));

            return result;
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error(e);
        }

        return null;
    }
}
