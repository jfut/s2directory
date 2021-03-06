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
package org.seasar.directory.dao.impl;

import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSourceFactory;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.impl.DeleteHandler;
import org.seasar.directory.impl.ExecuteHandler;

/**
 * 動的に削除処理を実行するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DeleteDynamicCommand extends AbstractDynamicDirectoryCommand {

	/**
	 * インスタンスを作成します。
	 * 
	 * @param dataSourceFactory
	 *            データソース
	 * @param attributeHandlerFactory
	 *            属性ハンドラファクトリ
	 * @param methodArgs
	 *            関数の引数
	 */
	public DeleteDynamicCommand(DirectoryDataSourceFactory dataSourceFactory,
			DirectoryAttributeHandlerFactory attributeHandlerFactory,
			AnnotationMethodArgs methodArgs) {
		super(dataSourceFactory, attributeHandlerFactory, null, methodArgs);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 削除処理を実行します。
	 * </p>
	 */
	public Object execute(Object[] args) {
		CommandContext ctx = apply(args);
		ExecuteHandler handler =
			new DeleteHandler(getDirectoryDataSource(args), ctx.getDn());
		return handler.execute();
	}

}
