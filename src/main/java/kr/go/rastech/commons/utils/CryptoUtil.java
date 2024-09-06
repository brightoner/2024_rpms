/******************************************************************
 * Copyright RASTECH 2015
 ******************************************************************/
package kr.go.rastech.commons.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.sql.SQLException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.util.PrivateKeyFactory;

import egovframework.rte.fdl.idgnr.impl.Base64;
import kr.go.rastech.commons.constants.Constants;
/**
 * <pre>
 * FileName: CryptoUtil.java
 * Package : kr.go.ncmik.commons.utils
 *
 * 암호화 관련한 유틸리티 객체.
 *
 * </pre>
 * @author : rastech
 * @date   : 2015. 3. 9.
 */
public class CryptoUtil {

    private static PaddedBufferedBlockCipher aES_CIPHER = null;
    private static ParametersWithIV pRIVATE_KEY = null;

    static {
        // 비밀키(16(128bit), 24(192bit),32(256bit)글자여야 함)
        // 256bit(32글자) 추천.
        String key = "a82224774f3b4b349e3f310c4b3b4c27";

        BlockCipher engine = new AESEngine();
        aES_CIPHER = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        pRIVATE_KEY = new ParametersWithIV(new KeyParameter(key.getBytes()), key.substring(0, 16).getBytes());

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     *
     * <pre>
     * 사용자가 입력한 비밀번호를 Scrypt을 사용하여 hash 한다.
     * 반환된 값은 DB에 저장된 비밀번호 hash 값이다.
     * one way function을 사용하여 비밀번호를 복구하지 못하도록 한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 9. 17.
     * @param pw
     * @return
     */
//    public static String toPassword(String pw) {
//    	if (pw == null) return null;
//
//        String hash = SCryptUtil.scrypt(pw, 16, 16, 16).substring(10);
//        return hash;
//    }

    /**
     *
     * <pre>
     * Scrypt로 비밀번호가 hash 된 것이 올바른지 확인한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 9. 17.
     * @param pw
     * @param hash
     * @return
     */
//    public static boolean checkPassword(String pw, String hash) {
//    	if (StringUtils.isEmpty(pw) || StringUtils.isEmpty(hash)) return false;
//
//    	return SCryptUtil.check(pw, PREFIX_PASSWORD + hash);
//    }

    /**
     *
     * <pre>
     * 비밀번호를 SSO에서 사용하는 hash 값으로 변환한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 11. 12.
     * @param pw
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
//    public static String toSSOPassword(String pw) throws IOException, SQLException , NullPointerException {
//    	if (pw == null) return null;
//
//    	MessageDigest md = MessageDigest.getInstance("SHA-256");
//    	byte[] hashValue = md.digest(pw.getBytes());
//
//    	return new String(Base64.encodeBase64(hashValue));
//    }

    /**
     *
     * <pre>
     * SSO에서 사용하는 비밀번호가 맞는지 확인한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 11. 13.
     * @param pw
     * @param hash
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
//    public static boolean checkSSOPassword(String pw, String hash) throws IOException, SQLException , NullPointerException {
//    	if (StringUtils.isEmpty(pw) || StringUtils.isEmpty(hash)) return false;
//
//    	return StringUtils.equals(toSSOPassword(pw), hash);
//    }

    /**
     *
     * <pre>
     * 이메일 인증에서 본인인증 키로 사용하는 hash 함수.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 10. 16.
     * @param txt
     * @return
     */
    public static String hashSHA(String txt) {
		/*String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((txt + HASH_SALT).getBytes());
			hash = Hex.encodeHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(CryptoUtil.class).debug("err hashSHA");
		}
		return hash;*/
    	return null;
    }

    /**
     *
     * <pre>
     * 평문을 AES-256으로 암호화 한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 3. 9.
     * @param plainText
     * @return
     */
    public static String encrypt(String plainText) {
    	if (plainText == null) return null;

        String encryptText = null;

        try {
            encryptText = Hex.encodeHexString(processing(plainText.getBytes(Constants.ENCODING_TYPE), true));
        } catch (DataLengthException e) {
        	Logger.getLogger(CryptoUtil.class).debug("ERROR DataLengthException encrypt");
		} catch (InvalidCipherTextException e) {
	    	Logger.getLogger(CryptoUtil.class).debug("ERROR InvalidCipherTextException encrypt");
		} catch (UnsupportedEncodingException e) {
	    	Logger.getLogger(CryptoUtil.class).debug("ERROR UnsupportedEncodingException encrypt");
		}

        return encryptText;
    }

    /**
     *
     * <pre>
     * 암호문을 AES-256으로 복호화 한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 3. 9.
     * @param encryptText
     * @return
     */
    public static String decrypt(String encryptText) {
        byte[] plainTextBytes = null;
        String plainText = null;

        try {
            plainTextBytes = processing(Hex.decodeHex(encryptText.toCharArray()), false);
            plainText = new String(plainTextBytes, Constants.ENCODING_TYPE);
        } catch (IOException e) {
        	Logger.getLogger(CryptoUtil.class).debug("ERROR IOException decrypt");
        } catch (DataLengthException e) {
        	Logger.getLogger(CryptoUtil.class).debug("ERROR DataLengthException decrypt");
		} catch (InvalidCipherTextException e) {
			Logger.getLogger(CryptoUtil.class).debug("ERROR InvalidCipherTextException decrypt");
		} catch (DecoderException e) {
			Logger.getLogger(CryptoUtil.class).debug("ERROR DecoderException decrypt");
		}

        return plainText;
    }

    /**
     *
     * <pre>
     * 암복호화 실행.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 3. 9.
     * @param input
     * @param encrypt
     * @return
     * @throws DataLengthException
     * @throws InvalidCipherTextException
     */
    private static byte[] processing(byte[] input, boolean encrypt) throws DataLengthException, InvalidCipherTextException {
    	aES_CIPHER.init(encrypt, pRIVATE_KEY);
        byte[] output = new byte[aES_CIPHER.getOutputSize(input.length)];
        int outputLen = aES_CIPHER.processBytes(input, 0, input.length, output, 0);
        outputLen += aES_CIPHER.doFinal(output, outputLen);

        byte[] result = new byte[outputLen];
        System.arraycopy(output, 0, result, 0, outputLen);
        return result;
    }

    /**
     *
     * <pre>
     * RSA Key쌍을 생성
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 11. 12.
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair genRASKey() throws NoSuchAlgorithmException {
    	KeyPairGenerator generator = org.bouncycastle.jce.provider.asymmetric.ec.KeyPairGenerator.getInstance("RSA");
		//generator.initialize(1024);
    	generator.initialize(2048);
		KeyPair keyPair = generator.genKeyPair();

		return keyPair;
    }

    /**
     *
     * <pre>
     * RSA 복호화.
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 11. 12.
     * @param text
     * @param key
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public static String decryptRSA(String text, PrivateKey key) throws IOException, SQLException , NullPointerException , InvalidCipherTextException {
    	if (text == null) return null;

        AsymmetricKeyParameter privateKey = PrivateKeyFactory.createKey(key.getEncoded());
        AsymmetricBlockCipher rsa = new RSAEngine();
        rsa = new PKCS1Encoding(rsa);
        rsa.init(false, privateKey);

        byte[] encryptText = hexToByteArray(text);
        byte[] decryptText = rsa.processBlock(encryptText, 0, encryptText.length);

        return new String(decryptText);
    }

    /**
     *
     * <pre>
     * 16진수 문자열을 byte 배열로 변환
     *
     * </pre>
     * @author : rastech
     * @date   : 2015. 11. 12.
     * @param hex
     * @return
     */
    private static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
    
	public static String shacode(String source) {
		String base64 = null;
		try {
			SHA256 s = new SHA256(source.getBytes());
		
			base64 = Base64.encode(s.getHashCode());
			
		} catch (NumberFormatException er) {
			Logger.getLogger(CryptoUtil.class).debug("err shacode");
		}
		return base64;
	}
}
