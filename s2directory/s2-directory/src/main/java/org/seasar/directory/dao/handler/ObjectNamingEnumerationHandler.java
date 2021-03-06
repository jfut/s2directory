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
package org.seasar.directory.dao.handler;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.util.DirectoryDataSourceUtil;

/**
 * 検索結果の1つ目の属性値を返すオブジェクト型ハンドラクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class ObjectNamingEnumerationHandler extends
		AbstractBeanMetaDataNamingEnumerationHandler {

	/**
	 * インスタンスを作成します。
	 * 
	 * @param beanMetaData
	 *            ビーンメタデータ
	 * @param property
	 *            ディレクトリサーバ接続情報
	 */
	public ObjectNamingEnumerationHandler(DirectoryBeanMetaData beanMetaData,
			DirectoryControlProperty property) {
		super(beanMetaData, property);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object handle(NamingEnumeration results, String baseDn)
			throws NamingException {
		if (results.hasMore()) {
			SearchResult result = (SearchResult)results.next();
			Attributes attributes = result.getAttributes();
			NamingEnumeration ae = attributes.getAll();
			if (ae.hasMore()) {
				Attribute attribute = (Attribute)ae.next();
				String attributeName = attribute.getID();
				return attributes.get(attributeName);
			}
			DirectoryDataSourceUtil.close(ae);
		}
		DirectoryDataSourceUtil.close(results);
		return null;
	}

}
