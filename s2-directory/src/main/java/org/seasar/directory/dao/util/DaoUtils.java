/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.directory.dao.util;

import org.seasar.directory.exception.DirectoryDaoNotFoundRuntimeException;

/**
 * Daoに関するユーティリティクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DaoUtils {
	/**
	 * 指定されたクラスのインタフェースのクラスインスタンスを返します。
	 * 
	 * @param clazz クラス
	 * @return インタフェースのクラスインスタンス
	 */
	public static Class getDaoInterface(Class clazz) {
		if (clazz.isInterface()) {
			return clazz;
		}
		throw new DirectoryDaoNotFoundRuntimeException(clazz);
	}
}