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
package org.seasar.directory.tiger.examples.client.common;

import org.seasar.directory.tiger.examples.dto.PosixAccountWithAnnotationDto;
import org.seasar.framework.container.S2Container;

/**
 * PosixAccountWithAnnotationDto生成クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class PosixAccountWithAnnotationDtoFactory extends DirectoryDtoFactory {

	/**
	 * 指定されたコンテナを持ったインスタンスを作成します。
	 * 
	 * @param container
	 */
	public PosixAccountWithAnnotationDtoFactory(S2Container container) {
		super(container);
	}

	/**
	 * PosixAccountDtoオブジェクトを作成します。
	 * 
	 * @param name
	 *            ユーザ名
	 * @return ユーザインスタンス
	 */
	public PosixAccountWithAnnotationDto getUser(String name) {
		PosixAccountWithAnnotationDto account =
			new PosixAccountWithAnnotationDto();
		account.setDn("uid=" + name + ",ou=Users," + property.getBaseDn());
		account.setCn(name);
		account.setPassword(name + "pass");
		account.setLoginShell("/bin/bash");
		account.setUid(name);
		account.setUidNumber("10001");
		account.setGid("1000");
		account.setGecos("System User");
		account.setHome("/home/users/" + name);
		account.setDescription("System User");
		// person オブジェクトクラス用に sn を設定します。
		// OpenLDAPの標準スキーマでは、SUPで下記の継承を持っているため。
		// posixAccount -> inetOrgPerson -> organizationPerson -> person
		// person MUST: sn cn
		account.setSn(name);
		return account;
	}

}
