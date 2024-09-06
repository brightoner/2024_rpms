package kr.go.rastech.commons.utils;

import egovframework.rte.fdl.idgnr.impl.Base64;

/**
* 암호화 Util Class
* 
* @author Administrator
* 
*/
public class Utils {
	public static final String KO_LOCALE = "ko_KR.EUC-KR";
	public static final String EN_LOCALE = "en";

	public static String encode(String source) {
		String base64 = null;
		/*try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = source.getBytes("UTF-8");
			
			//BASE64Encoder encoder = new BASE64Encoder();
			//base64 = encoder.encode(md.digest(bytes));
			
			base64 = Base64.encode(md.digest(bytes));
		
		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(Utils.class).debug("err NoSuchAlgorithmException encode");
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger(Utils.class).debug("err UnsupportedEncodingException encode");
		}*/
		return base64;
	}

	public static String shacode(String source) {
		String base64 = null;

		SHA256 s = new SHA256(source.getBytes());

		
		base64 = Base64.encode(s.getHashCode());

		return base64;
	}
	
	/**
	 * NCMIK 패스워드 암호화 공통 룰
	 * @param password - 암호화 되기 전 평문 문장
	 * @param emplyrkey - 유저 로그인 키
	 * @param sha2 - 유저 암호화 규칙
	 * @return 암호화된 문장(password)
	 */
	public static String passwordEncryption(String password, String emplyrkey, String sha2) {
		String sha = "X";
		
		if ("E".equals(sha2)) {
			sha = "M";
		}
		else if ("Y".equals(sha2)) {
			sha = "S";
		}
		else if ("D".equals(sha2)) {
			sha = "D";
		}
		
		String userPw = encode(password);
		String shapw = shacode(password + emplyrkey);
		
		password = "M".equals(sha) || "D".equals(sha) ? userPw : shapw;
		
		return password;
	}
}
