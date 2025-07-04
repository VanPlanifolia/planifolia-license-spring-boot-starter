package van.planifolia.license.util;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class RsaUtil {

    private static final String RSA_ALGORITHM = "RSA";

    /**
     * 签名方法
     *
     * @param content    正文
     * @param privateKey 私钥Key
     * @return 签名后的正文
     */
    public static String sign(String content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] signBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signBytes);
    }

    /**
     * 解析公私钥key，本质上就是base64编码
     *
     * @param key 被解析的
     * @return 解析结果
     */
    public static String encodeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 验签
     *
     * @param content         被验证的正文
     * @param base64Signature 被验证的密文
     * @param publicKey       公钥
     * @return 校验结果
     */
    public static boolean verify(String content, String base64Signature, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] signBytes = Base64.getDecoder().decode(base64Signature);
        return signature.verify(signBytes);
    }

    public static PublicKey decodePublicKey(String base64Key) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public static PrivateKey decodePrivateKey(String base64Key) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    /**
     * 生成一对公私钥
     *
     * @return 生成的公私钥对
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        generator.initialize(2048);
        return generator.generateKeyPair();
    }


}
