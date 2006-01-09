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
package org.seasar.directory;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * サーチ結果を扱うインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface NamingEnumerationHandler {
	/**
	 * 指定されたサーチ結果を処理します。
	 * 
	 * @param results - サーチ結果
	 * @return
	 * @throws NamingException
	 */
	public Object handle(NamingEnumeration results, String baseDn)
			throws NamingException;
}