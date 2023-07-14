package com.webuilding.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class XRsaUtil {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    public static final String RSA_ALGORITHM_SIGN = "SHA256WithRSA";

    private static RSAPublicKey publicKey;
    private static RSAPrivateKey privateKey;
    //可以调用 createKeys 方法自己生成，替换一下



    //解密加密
    public static String setRsaPublicKey(Object json,String publicKeyValue){
        //String publicKeyValue = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAILxLkw91qVRcID_sDpbVSkK1B7mnBpE_eOkU6bK3t-BH7iWgByvsmmwgrIDU2B1m1v7pNJYu4mljHzpDj0XYNECAwEAAQ";
        String en = publicEncrypt(json.toString(), getRSAPublicKey(publicKeyValue));
        return en;
    }

    //解密
    public static String getData(String json,String privateKeyValue){
        //String privateKeyValue ="MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAgvEuTD3WpVFwgP-wOltVKQrUHuacGkT946RTpsre34EfuJaAHK-yabCCsgNTYHWbW_uk0li7iaWMfOkOPRdg0QIDAQABAkASlVIBxgDxg2ZZGHCVR6MFaSEDpazf2YzCwu6QTFhnFcMK3z-VXOziZhMw0KYUFMzwQEQu4cKK7olcFFN8poRBAiEAyXi3bI91gkVM3zdbk_8fu5QrEI6kLrj0ydWY6MtHeJkCIQCmYbfCehWMAhFoja3AL5NVovRgNnC4LIISX5gpKTQ0-QIgKxse86VGGRdGuUOY3nNpkLLE_Afo7O45wa1nx_cmVZECIQCRghY2Q5TCfDCDUpyo3jKpCzlTR2ku-OXMccPeA4X_6QIgOl1a5cIxSvXyBnuYGx5ZKS-Yst_BoyMQxMVRgr4bmv4";
        String de = XRsaUtil.privateDecrypt(json, getRSAPrivateKey(privateKeyValue));
        return de;
    }


    public XRsaUtil(String publicKey, String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

            // 通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));

            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            // 通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            throw new RuntimeException("Unsupported key: ", e);
        }
    }

    public static RSAPublicKey getRSAPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            // 通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            throw new RuntimeException("getRSAPublicKey,Unsupported key: ", e);
        }
    }

    public static RSAPrivateKey getRSAPrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            // 通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            throw new RuntimeException("getRSAPrivateKey,Unsupported key: ", e);
        }
    }


    public static Map<String, String> createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;

        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm -> [" +
                    RSA_ALGORITHM + "]");
        }

        // 初始化KeyPairGenerator对象,不要被initialize()源码表面上欺骗,其实这里声明的size是生效的
        kpg.initialize(keySize);

        // 生成秘钥对
        KeyPair keyPair = kpg.generateKeyPair();

        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());

        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 公钥加密
     */
    public String publicEncrypt(String data) {
        return publicKeyEncrypt(data, publicKey);
    }
    public static String publicEncrypt(String data, RSAPublicKey rsaPublicKey) {
        return publicKeyEncrypt(data, rsaPublicKey);
    }

    private static String publicKeyEncrypt(String data, RSAPublicKey rsaPublicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);

            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher,
                    Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
                    rsaPublicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("Unsupported key[" + data + "]", e);
        }
    }

    /**
     * 私钥解密
     */
    public String privateDecrypt(String data) {
        return privateKeyDecrypt(data, privateKey, publicKey.getModulus());
    }
    public static String privateDecrypt(String data, RSAPrivateKey rsaPrivateKey) {
        return privateKeyDecrypt(data, rsaPrivateKey, rsaPrivateKey.getModulus());
    }

    private static String privateKeyDecrypt(String data, RSAPrivateKey rsaPrivateKey, BigInteger modulus) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);

            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    modulus.bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("Unsupported key[" + data + "]", e);
        }
    }

    /**
     * 私钥加密
     */
    public static String privateEncrypt(String data) {
        return privateKeyEncrypt(data, privateKey, publicKey.getModulus());
    }
    public static String privateEncrypt(String data, RSAPrivateKey rsaPrivateKey) {
        return privateKeyEncrypt(data, rsaPrivateKey, rsaPrivateKey.getModulus());
    }

    private static String privateKeyEncrypt(String data, RSAPrivateKey rsaPrivateKey, BigInteger modulus) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);

            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher,
                    Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
                    modulus.bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("Unsupported key[" + data + "]", e);
        }
    }

    /**
     * 公钥解密
     */
    public String publicDecrypt(String data) {
        return publicKeyDecrypt(data, publicKey);
    }
    public static String publicDecrypt(String data, RSAPublicKey rsaPublicKey) {
        return publicKeyDecrypt(data, rsaPublicKey);
    }

    private static String publicKeyDecrypt(String data, RSAPublicKey rsaPublicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);

            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE,
                    Base64.decodeBase64(data),
                    rsaPublicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("Unsupported key[" + data + "]", e);
        }
    }

    /**
     * 私钥签名
     */
    public String sign(String data) {
        return getSign(data, privateKey);
    }

    public static String sign(String data, RSAPrivateKey privateKey) {
        return getSign(data, privateKey);
    }

    private static String getSign(String data, RSAPrivateKey privateKey) {
        try {
            String encodeStr = DigestUtils.md5Hex(data);
            Signature signature = Signature.getInstance(RSA_ALGORITHM_SIGN);
            signature.initSign(privateKey);
            signature.update(encodeStr.getBytes(CHARSET));

            return Base64.encodeBase64URLSafeString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("Unsupported key[" + data + "]", e);
        }
    }

    /**
     * 公钥验签，验证sign
     */
    public boolean verify(String data, String sign) {
        return verifySign(data, sign, publicKey);
    }

    public static boolean verify(String data, String sign, RSAPublicKey rsaPublicKey) {
        return verifySign(data, sign, rsaPublicKey);
    }

    public static boolean verifySign(String data, String sign, RSAPublicKey rsaPublicKey) {
        try {
            String encodeStr = DigestUtils.md5Hex(data);
            Signature signature = Signature.getInstance(RSA_ALGORITHM_SIGN);
            signature.initVerify(rsaPublicKey);
            signature.update(encodeStr.getBytes(CHARSET));

            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            throw new RuntimeException("[" + data + "]", e);
        }
    }

    /**
     * 分段加解密
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode,
                                        byte[] datas, int keySize) {
        int maxBlock = 0;

        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = (keySize / 8) - 11;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;

        try {
            while (datas.length > offSet) {
                if ((datas.length - offSet) > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }

                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("Unsupported key[" + maxBlock + "]", e);
        }

        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }
    /**
     * 公钥分段加密
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @param length 段长
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(byte[] data, String publicKey,int length) {
        ByteArrayOutputStream out = null;
        byte[] encryptData = null;
        try {
            byte[] keyBytes = decryptBASE64(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > length) {
                    cache = cipher.doFinal(data, offSet, length);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * length;
            }
            encryptData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encryptData;
    }
    private static byte[] decryptBASE64(String src) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(src);
        } catch (Exception ex) {
            return null;
        }
    }
/** *//**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    private static byte[] decryptByPrivateKey(byte[] data, String privateKey,int length){
        ByteArrayOutputStream out = null;
        byte[] decryptedData = null;
        try {
            byte[] keyBytes =  decryptBASE64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > length) {
                    cache = cipher.doFinal(data, offSet, length);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * length;
            }
            decryptedData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return decryptedData;
    }


    public static void main(String[] args) throws Exception {
        // 生成 RSA 密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        // 设置要加密的数据
        String data = "Hello, world!";
        // 计算分段大小
        int chunkSize = 117;
        // 使用公钥加密数据
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = new byte[0];
        for (int i = 0; i < data.length(); i += chunkSize) {
            byte[] chunk = data.substring(i, Math.min(i + chunkSize, data.length())).getBytes("UTF-8");
            byte[] encryptedChunk = encryptCipher.doFinal(chunk);
            encrypted = concatenate(encrypted, encryptedChunk);
        }
        System.out.println("Encrypted: " + Base64.encodeBase64String(encrypted));
        // 使用私钥解密数据
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decrypted = new byte[0];
        for (int i = 0; i < encrypted.length; i += chunkSize) {
            byte[] chunk = subarray(encrypted, i, i + chunkSize);
            byte[] decryptedChunk = decryptCipher.doFinal(chunk);
            decrypted = concatenate(decrypted, decryptedChunk);
        }
        System.out.println("Decrypted: " + new String(decrypted, "UTF-8"));
    }
    private static byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    private static byte[] subarray(byte[] array, int start, int end) {
        int length = end - start;
        byte[] result = new byte[length];
        System.arraycopy(array, start, result, 0, length);
        return result;
    }

}
