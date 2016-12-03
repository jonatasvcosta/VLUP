package politcc2017.tcc_app.Components.Helpers;


import java.security.GeneralSecurityException;

import politcc2017.tcc_app.Components.Helpers.AesCbcWithIntegrity.AesCbcWithIntegrity;

/**
 * Created by Jonatas on 01/12/2016.
 */
public class EncryptionHelper {
    private static final String KEY = "abc";//"N@Ü!l#$";
    private static final String SALT = "acd";//"J*()Lç~";
    private static AesCbcWithIntegrity.SecretKeys key;

    public static void Init(){
        try {
            key = AesCbcWithIntegrity.generateKey();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
        public static String Encrypt(String value) {
            try {
                String ret = AesCbcWithIntegrity.encrypt("some test", key).toString();
                return ret;
            } catch (Exception e) {
                return value;
            }
        }

        public static String Decrypt(String s) {
            AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = new AesCbcWithIntegrity.CipherTextIvMac(s);
            try {
                String ret = AesCbcWithIntegrity.decryptString(cipherTextIvMac, key);
                return ret;
            } catch (Exception e) {
                return s;
            }
        }

    }