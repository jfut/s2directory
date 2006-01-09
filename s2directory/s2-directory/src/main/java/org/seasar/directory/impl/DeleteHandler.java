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
package org.seasar.directory.impl;

import org.seasar.directory.DirectoryDataSource;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;

/**
 * 削除を処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DeleteHandler extends BasicHandler implements ExecuteHandler {
	/** ロガーを表わします。 */
	private static Logger logger = Logger.getLogger(BasicSelectHandler.class);
	/** 削除する識別譜を表します。 */
	private String dn;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryDataSource
	 * @param dn
	 */
	public DeleteHandler(DirectoryDataSource directoryDataSource, String dn) {
		super(directoryDataSource);
		this.dn = dn;
	}

	/**
	 * 処理を実行します。
	 * 
	 * @return
	 * @throws NamingRuntimeException
	 * @see org.seasar.directory.impl.ExecuteHandler#execute()
	 */
	public Object execute() throws NamingRuntimeException {
		if (logger.isDebugEnabled()) {
			logger.debug("Delete: " + dn);
		}
		return super.delete(dn);
	}
}