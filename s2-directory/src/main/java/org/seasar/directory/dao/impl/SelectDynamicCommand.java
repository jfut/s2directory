/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.impl.ExecuteHandler;
import org.seasar.directory.impl.SelectHandler;

/**
 * 動的に読み出し処理を実行するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class SelectDynamicCommand extends AbstractDynamicDirectoryCommand {
	/** 検索結果ハンドラ */
	private NamingEnumerationHandler namingEnumerationHandler;

	/**
	 * @param dataSource
	 *            データソース
	 * @param namingEnumerationHandler
	 *            検索結果ハンドラ
	 * @param attributeHandlerFactory
	 *            属性ハンドラファクトリ
	 * @param methodArgs
	 *            関数の引数
	 */
	public SelectDynamicCommand(DirectoryDataSourceFactory dataSourceFactory,
			DirectoryAttributeHandlerFactory attributeHandlerFactory,
			AnnotationMethodArgs methodArgs,
			NamingEnumerationHandler namingEnumerationHandler) {
		super(dataSourceFactory, attributeHandlerFactory, methodArgs);
		this.namingEnumerationHandler = namingEnumerationHandler;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 読み出し処理を実行します。
	 * </p>
	 */
	public Object execute(Object[] args) {
		CommandContext ctx = apply(args);
		// TODO: ここでコマンドコンテキストを渡しちゃうのは良くない
		// 将来的には BindVariables のようなものを使うようなものに直す
		ExecuteHandler selectHandler =
			new SelectHandler(getDirectoryDataSource(args), super
				.getRunFilter(ctx), namingEnumerationHandler, ctx);
		return selectHandler.execute();
	}
}