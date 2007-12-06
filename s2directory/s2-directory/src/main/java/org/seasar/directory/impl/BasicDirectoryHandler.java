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
package org.seasar.directory.impl;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.exception.DirectoryNameAlreadyBoundRuntimeException;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.util.DirectoryDataSourceUtil;
import org.seasar.directory.util.DirectoryUtil;
import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * ディレクトリサーバと接続し、処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class BasicDirectoryHandler {
	/** データソース */
	private DirectoryDataSource directoryDataSource;
	/** ディレクトリサーバ接続情報 */
	protected DirectoryControlProperty directoryControlProperty;

	/**
	 * 指定したデータソースを持ったインスタンスを作成します。
	 * 
	 * @param directoryDataSource
	 */
	public BasicDirectoryHandler(DirectoryDataSource directoryDataSource) {
		this.directoryDataSource = directoryDataSource;
		this.directoryControlProperty =
			directoryDataSource.getDirectoryControlProperty();
	}

	/**
	 * directoryDataSourceを取得します。
	 * 
	 * @return directoryDataSource
	 */
	public DirectoryDataSource getDirectoryDataSource() {
		return directoryDataSource;
	}

	/**
	 * directoryDataSourceを設定します。
	 * 
	 * @param directoryDataSource
	 */
	public void setDirectoryDataSource(DirectoryDataSource directoryDataSource) {
		this.directoryDataSource = directoryDataSource;
	}

	/**
	 * ディレクトリコネクションを取得します。
	 * 
	 * @return ディレクトリコネクション
	 */
	protected DirContext getConnection() {
		if (directoryDataSource == null) {
			throw new EmptyRuntimeException("directoryDataSource");
		}
		return DirectoryDataSourceUtil.getConnection(directoryDataSource);
	}

	/**
	 * 認証を実行します。
	 * 
	 * @return 認証結果を返します。
	 */
	public Boolean authenticate() {
		if (directoryDataSource == null) {
			throw new EmptyRuntimeException("directoryDataSource");
		}
		return new Boolean(DirectoryDataSourceUtil
			.authenticate(directoryDataSource));
	}

	/**
	 * 検索を実行します。
	 * 
	 * @return 検索結果を返します。
	 */
	public NamingEnumeration search(String filter) {
		return search(filter, directoryControlProperty.getBaseDn());
	}

	/**
	 * 検索を実行します。
	 * 
	 * @return 検索結果を返します。
	 */
	public NamingEnumeration search(String filter, String baseDn) {
		SearchControls controls = new SearchControls();
		controls.setSearchScope(directoryControlProperty.getSearchControls());
		DirContext context = null;
		try {
			context = getConnection();
			return context.search(baseDn, filter, controls);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 作成を実行します。
	 * 
	 * @return 作成した数を返します。
	 */
	public Integer insert(String dn, Attributes attrs) {
		DirContext context = null;
		try {
			context = getConnection();
			context.createSubcontext(dn, attrs);
			return new Integer(1);
		} catch (NameAlreadyBoundException e) {
			throw new DirectoryNameAlreadyBoundRuntimeException(e);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 更新を実行します。
	 * 
	 * @return 更新した数を返します。
	 */
	public Integer update(String dn, ModificationItem[] items) {
		if (items.length == 0)
			return new Integer(0);
		DirContext context = null;
		try {
			context = getConnection();
			context.modifyAttributes(dn, items);
			return new Integer(1);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 削除を実行します。
	 * 
	 * @return 削除した数を返します。
	 */
	public Integer delete(String dn) {
		DirContext context = null;
		try {
			String firstDn = DirectoryUtil.getFirstDn(dn);
			String baseDn = DirectoryUtil.getBaseDn(dn);
			NamingEnumeration results = search(firstDn, baseDn);
			context = getConnection();
			context.destroySubcontext(dn);
			return count(results);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 検索結果の数を返します。
	 * 
	 * @param results
	 *            検索結果
	 * @return 検索結果の数
	 * @throws NamingException
	 */
	public Integer count(NamingEnumeration results) throws NamingException {
		int count = 0;
		while (results.hasMore()) {
			results.next();
			count++;
		}
		return new Integer(count);
	}
}
