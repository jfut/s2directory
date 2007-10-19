/*
 * Copyright 2005-2006 the Seasar Foundation and the Others.
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
package org.seasar.directory.dao;

/**
 * DirectoryDaoMetaDataを生成するインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryDaoMetaDataFactory {
	/**
	 * 指定されたDAOクラスから生成したDirecotryDaoMetaDataのインスタンスを返します。
	 * 
	 * @param daoClass 生成元となるDAOクラス
	 * @return 生成したDirecotryDaoMetaDataのインスタンス
	 */
	public DirectoryDaoMetaData getDirectoryDaoMetaData(Class daoClass);
}
