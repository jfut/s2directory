/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.directory.digest;

import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

/**
 * DigestFactoryのテストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DigestFactoryTest extends TestCase {

	/**
	 * MD5のテストを行います。
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void testPlainDigest() throws NoSuchAlgorithmException {
		String pass = "secret";
		String hash = "secret";
		Digest digest = DigestFactory.getDigest(hash);
		assertEquals("{PLAIN}", digest.getLabel());
		assertEquals(hash, digest.create(pass));
		assertEquals(true, digest.verify(hash, pass));
		hash = digest.create(pass);
		assertEquals(true, digest.verify(hash, pass));
		digest = DigestFactory.getDigest("PLAIN");
		assertEquals(true, digest.verify(hash, pass));
	}

	/**
	 * MD5のテストを行います。
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void testMD5Digest() throws NoSuchAlgorithmException {
		String pass = "secret";
		String hash = "{MD5}Xr4ilOzQ4PCOq3aQ0qbuaQ==";
		Digest digest = DigestFactory.getDigest(hash);
		assertEquals("{MD5}", digest.getLabel());
		assertEquals(hash, digest.create(pass));
		assertEquals(true, digest.verify(hash, pass));
		hash = digest.create(pass);
		assertEquals(true, digest.verify(hash, pass));
		digest = DigestFactory.getDigest("MD5");
		assertEquals(true, digest.verify(hash, pass));
	}

	/**
	 * SMD5のテストを行います。
	 */
	public void testSMD5Digest1() throws NoSuchAlgorithmException {
		String pass = "secret";
		String hash = "{SMD5}KNaOGWWWmCgJou1M6cUgt0l1QyA=";
		Digest digest = DigestFactory.getDigest(hash);
		assertEquals("{SMD5}", digest.getLabel());
		assertEquals(true, digest.verify(hash, pass));
		hash = digest.create(pass);
		assertEquals(true, digest.verify(hash, pass));
		digest = DigestFactory.getDigest("SMD5");
		assertEquals(true, digest.verify(hash, pass));
	}

	/**
	 * SMD5のテストを行います。
	 */
	public void testSMD5Digest2() throws NoSuchAlgorithmException {
		int saltLength = 8;
		String pass = "secret";
		String hash = "{SMD5}F5Z9wOVz7wHf/Vq9YSKIwnUxUitDPUpm";
		Digest digest = DigestFactory.getDigest(hash);
		assertEquals("{SMD5}", digest.getLabel());
		assertEquals(true, digest.verify(hash, pass));
		hash = digest.create(pass, saltLength);
		assertEquals(true, digest.verify(hash, pass));
		digest = DigestFactory.getDigest("SMD5");
		assertEquals(true, digest.verify(hash, pass));
	}

	/**
	 * SHAのテストを行います。
	 */
	public void testSHADigest() throws NoSuchAlgorithmException {
		String pass = "secret";
		String hash = "{SHA}5en6G6MezRroT3XKqkdPOmY/BfQ=";
		Digest digest = DigestFactory.getDigest(hash);
		assertEquals("{SHA}", digest.getLabel());
		assertEquals(hash, digest.create(pass));
		assertEquals(true, digest.verify(hash, pass));
		hash = digest.create(pass);
		assertEquals(true, digest.verify(hash, pass));
		digest = DigestFactory.getDigest("SHA");
		assertEquals(true, digest.verify(hash, pass));
	}

	/**
	 * SSHAのテストを行います。
	 */
	public void testSSHADigest1() throws NoSuchAlgorithmException {
		String pass = "secret";
		String hash = "{SSHA}DeqCMzuWq1PMxsXCjLgEc3llb8Kmr1UK";
		Digest digest = DigestFactory.getDigest(hash);
		assertEquals("{SSHA}", digest.getLabel());
		assertEquals(true, digest.verify(hash, pass));
		hash = digest.create(pass);
		assertEquals(true, digest.verify(hash, pass));
		digest = DigestFactory.getDigest("SSHA");
		assertEquals(true, digest.verify(hash, pass));
	}

	/**
	 * SSHAのテストを行います。
	 */
	public void testSSHADigest2() throws NoSuchAlgorithmException {
		int saltLength = 8;
		String pass = "secret";
		String hash = "{SSHA}h+nfjiWESqi/sC1s7EsX82RHr4wqQFthISFgeA==";
		Digest digest = DigestFactory.getDigest(hash);
		assertEquals("{SSHA}", digest.getLabel());
		assertEquals(true, digest.verify(hash, pass));
		hash = digest.create(pass, saltLength);
		assertEquals(true, digest.verify(hash, pass));
		digest = DigestFactory.getDigest("SSHA");
		assertEquals(true, digest.verify(hash, pass));
	}

	/**
	 * 存在しないアルゴリズムのテストを行います。
	 */
	public void testUnknownDigest1() {
		try {
			Digest digest = DigestFactory.getDigest("{UNKNOWN}");
			digest.create("secret");
		} catch (NoSuchAlgorithmException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

}
