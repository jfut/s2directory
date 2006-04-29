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

import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.AnnotationMethodArgs;

/**
 * 新規追加処理を実行するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class InsertAutoCommand extends AbstractAutoDirectoryCommand {
	/**
	 * インスタンスを作成します。
	 * 
	 * @param dataSource - データソース
	 */
	public InsertAutoCommand(DirectoryDataSource dataSource,
			AnnotationMethodArgs methodArgs) {
		super(dataSource, methodArgs);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 新規追加処理を実行します。
	 * </p>
	 */
	public Object execute(Object[] args) {
		CommandContext cmd = apply(args);
		ExecuteHandler handler = new InsertHandler(
				getDirectoryDataSource(args), cmd);
		return handler.execute();
	}
}