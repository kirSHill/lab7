package server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public abstract class Encryptor {

    public static String encryptPassword(String password)
    {
        String sha384 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-384");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha384 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha384;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
