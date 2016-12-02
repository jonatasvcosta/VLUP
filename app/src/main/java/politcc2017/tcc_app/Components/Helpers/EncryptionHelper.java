package politcc2017.tcc_app.Components.Helpers;

import se.simbio.encryption.Encryption;


/**
 * Created by Jonatas on 01/12/2016.
 */
public class EncryptionHelper {
    private static final String KEY = "abc";//"N@Ü!l#$";
    private static final String SALT = "acd";//"J*()Lç~";
    private static Encryption encryption;

    public static void Init(){
        //byte[] IV =  {-17, 44, 19, -5, 0, 96, -4, 81, 123, 11, 61, -48, -17, 21, 53, 5};
        //encryption = Encryption.getDefault("key", "salt", new byte[16]);

    }
        public static String Encrypt(String value) {
            encryption = Encryption.getDefault("key", "salt", new byte[16]);
            return encryption.encryptOrNull(value);
        }

        public static String Decrypt(String encrypted) {
            encryption = Encryption.getDefault("key", "salt", new byte[16]);
            return encryption.decryptOrNull(encrypted);
        }

    }