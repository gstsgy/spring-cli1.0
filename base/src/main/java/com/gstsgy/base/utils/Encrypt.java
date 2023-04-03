package com.gstsgy.base.utils;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guyue
 * @version 0.0.0.1
 * @date 2019-12-26
 * @desc 数据加密相关
 */
public class Encrypt {
    /**
     * 进行MD5加密
     *
     * @param info 要加密的信息
     * @return String 加密后的字符串
     */
    public static String encryptToMD5(String info) {
        byte[] digesta = null;
        try {
            //info=encryptSimple(info);
            // 得到一个md5的消息摘要
            MessageDigest alga = MessageDigest.getInstance("MD5");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        assert digesta != null;
        return byte2hex(digesta);
    }
    /**
     * 进行MD5加密
     *
     * @param info 要加密的信息
     * @return String 加密后的字符串
     */
    public static byte[] encryptToMD5toHex(String info) {
        byte[] digesta = null;
        try {
            //info=encryptSimple(info);
            // 得到一个md5的消息摘要
            MessageDigest alga = MessageDigest.getInstance("MD5");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        assert digesta != null;
        return digesta;
    }

    public static String encryptSimple(String content){
        if(content==null)return null;
        char[] bytes = content.toCharArray();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (char) (bytes[i]^128);
        }

        return String.valueOf(bytes);
    }
    /**
     * 进行SHA加密
     *
     * @param info 要加密的信息
     * @return String 加密后的字符串
     */
    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
            // 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        return byte2hex(digesta);
    }

    /**
     * AES加密字符串
     *
     * @param content  需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public static byte[] encryptToAES(String content, String password) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
            byte[] result;// 加密
            result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decryptToAES(byte[] content, String password) {
        try {

            //kg.init(keysize, secureRandom);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result;
            result = cipher.doFinal(content);
            return result; // 明文
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptToRSA(String str, String publicKey) throws Exception {
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decryptToRSA(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str);
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer, String> genRSAKeyPair() throws NoSuchAlgorithmException {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded(),false));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64(privateKey.getEncoded(),false));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
        return keyMap;
    }


    /**
     * 将二进制转化为16进制字符串
     *
     * @param buf 二进制字节数组
     * @return String
     */
    public static String byte2hex(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();

    }

    /**
     * 十六进制字符串转化为2进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] hex2byte(String hexStr) {
        if (hexStr.length() < 1){
            return null;
        }

        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}));
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}));
        return (byte) (_b0 ^ _b1);
    }

    /**
     * 将指定的对象写入指定的文件
     *
     * @param file 指定写入的文件
     * @param objs 要写入的对象
     */
    public static void doObjToFile(String file, Object[] objs) {
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            for (int i = 0; i < objs.length; i++) {
                oos.writeObject(objs[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回在文件中指定位置的对象
     *
     * @param file 指定的文件
     * @param i    从1开始
     * @return
     */
    public static Object getObjFromFile(String file, int i) {
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            for (int j = 0; j < i; j++) {
                obj = ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

//    public static void main(String[] args) throws Exception {
//        //System.out.println(encryptToMD5("suray2020"));
//        //Map map = genRSAKeyPair();
//        //System.out.println(map);
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDa6FwCsSCF8l9hH0n1gGM43utxWOf1PSyCed4bJogg4TBB1Ud3Ou4QIdktmol6730lF6Imwq7ZMQnbZl7vxlK2pmm1lqmcA4GKki8qlsUIKMet5ozrEekPDOx9NHH6Y6Ym6EHM1ueIiRQMcRhrNnCajc6pcG5T1Ul8xmuhcZv2MQIDAQAB";
//        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANroXAKxIIXyX2EfSfWAYzje63FY5/U9LIJ53hsmiCDhMEHVR3c67hAh2S2aiXrvfSUXoibCrtkxCdtmXu/GUramabWWqZwDgYqSLyqWxQgox63mjOsR6Q8M7H00cfpjpiboQczW54iJFAxxGGs2cJqNzqlwblPVSXzGa6Fxm/YxAgMBAAECgYBX6T6iOh30jOxu5ZDfDctLGIL3BxxIKWQJlgVzY9sBUU3+8jb8E+dtBRrhrJEPlMPuNHqDFGYBhScPPCJeKogmYS65OxY8FWuf8HOXaAwe+/ghYiwoURdX3RcBXRbKgjHGaDCQPgdEMOOzvVySJferH/lV6Dx/Rr0cGVHuIJEnwQJBAP73sDZWc/6GHnc0iDRejD7TjIel2yropivHXh3i1iZKfKSEGZ9I1HRvrSk8hHx7Zydpb0Ak89wqHJ9chl8lnFkCQQDby0ooCnxTOjmN7ORG/qjjySTmCqrPJSD8AIPY5j1G0cqYzWKdeWOl6b9XkxJYjiduBEqBMLjfuNtHti3KmQ2ZAkEA9kW8eN/Mrn5Rkwa0wRDKGi0wQh3bsniBeSvbJrbh7p5oAmhub0JjpAvykDAaMDrqClF22dhXi8NJXkx6phCoEQJBAJbMLyCxsQLZG8vIEAa1ieJeirZZ8z8T1kGPiOYzcS85mKqln1czha67ZNAM07V2Mo9E2mkwqGa2SZw13cz2nWECQQCON4MC8R0rc7g3BVcluJqUh1Kq0jnMnzmkdZKP0PF2352mwRWTp4/4RzWad8mY0YWCTENSaveN7A4LqMShdFWV";
//
//       // System.out.println(com.gstsgy.common.utils.DateUtil.getDateTime());
//
//        System.out.println(Encrypt.encryptToRSA("2022-08-24 16:59:00#2023-08-24 16:59:00",publicKey));
//       // System.out.println(Encrypt.decryptToRSA("Afrmaa3TkqXYS8iVDKSxj16J5kA/iikGm5R1lgZUaX+cBYmXuIuJjnWW6Zpuye53S9w9SzPz6LNL8ws8FfotlIrjjW+kRxL5Fsg4m6F84w6E43t60UJ4DzvVE8A4B4Wm+26bmHmJ7OefcV0KRomMBzebyM1r7VpHlIjZ20K0t9E=",privateKey));
//    }
}
