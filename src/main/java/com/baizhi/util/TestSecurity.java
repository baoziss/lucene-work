package com.baizhi.util;

import java.util.Map;

public class TestSecurity {
	public static void main(String[] args) throws Exception {
		test01();
	}
	/**
	 * 非对称加密
	 * @throws Exception
	 */
	public static void test01() throws Exception{
		//随机获取公钥私钥
		Map<String, Object> keyMap = RSAUtils.genKeyPair();
		String privateKey = RSAUtils.getPrivateKey(keyMap);
		String publicKey=RSAUtils.getPublicKey(keyMap);
		
		//私钥加密  公钥 解密  服务器-客户端
		byte[] bytes1=RSAUtils.encryptByPrivateKey("我爱中国".getBytes(), privateKey);
		System.out.println("加密后："+new String(bytes1));
		String decode1 = new String(RSAUtils.decryptByPublicKey(bytes1, publicKey));
		System.out.println(decode1);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		//公钥加密 私钥解密  客户端-服务器
		byte[] bytes2=RSAUtils.encryptByPublicKey("我爱java".getBytes(), publicKey);
		System.out.println("加密后："+new String(bytes2));
		String decode2 = new String(RSAUtils.decryptByPrivateKey(bytes2, privateKey));
		System.out.println(decode2);
		

	}
	/**
	 * 对称加密
	 * @throws Exception
	 */
	public static void test02() throws Exception{
		String content = "I hate java";
		System.out.println("加密前：" + content);
		String key = "i love java";
		String encrypt = AESUtil.aesEncrypt(content, key);
		System.out.println("加密后：" + encrypt);
		String decrypt = AESUtil.aesDecrypt(encrypt, "i lave java");
		System.out.println("解密后：" + decrypt);
	}
}
