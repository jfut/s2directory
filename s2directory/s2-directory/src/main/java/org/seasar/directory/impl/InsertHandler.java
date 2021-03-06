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
package org.seasar.directory.impl;

import java.util.Iterator;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.attribute.AttributeHandler;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.types.PropertyType;
import org.seasar.directory.util.DirectoryUtil;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;

/**
 * 新規追加処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class InsertHandler extends BasicDirectoryHandler implements
		ExecuteHandler {

	/** ロガー */
	private static Logger logger = Logger.getLogger(SelectHandler.class);

	/** ビーンメタデータ */
	private DirectoryBeanMetaData beanMetaData;

	/** 引数をコマンドとみなしたコンテキスト */
	private CommandContext ctx;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param dataSource
	 * @param ctx
	 */
	public InsertHandler(DirectoryDataSource dataSource,
			DirectoryBeanMetaData beanMetaData, CommandContext ctx) {
		super(dataSource);
		this.beanMetaData = beanMetaData;
		this.ctx = ctx;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 新規追加処理を実行します。
	 * </p>
	 */
	public Object execute() throws NamingRuntimeException {
		if (logger.isDebugEnabled()) {
			logger.debug("Insert: " + ctx.getDn());
			String[] objectClasses = ctx.getObjectClasses();
			for (int i = 0; i < objectClasses.length; i++) {
				logger.debug("\tobjectClass: " + objectClasses[i]);
			}
		}
		return insert(ctx.getDn());
	}

	/**
	 * 指定されたエントリに追加します。
	 * 
	 * @return 追加した数を返します。
	 */
	private Integer insert(String dn) {
		try {
			return super.insert(dn, createAttributes(dn));
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		}
	}

	/**
	 * エントリを作成します。
	 * 
	 * @param result
	 * @param attributeNameSet
	 * @return
	 * @throws NamingException
	 */
	protected Attributes createAttributes(String dn) throws NamingException {
		String firstDn = DirectoryUtil.getFirstDn(dn);
		String dnName = DirectoryUtil.getAttributeName(firstDn);
		String dnValue = DirectoryUtil.getAttributeValue(firstDn);
		Attributes entry = new BasicAttributes(dnName, dnValue);
		// オブジェクトクラスを設定します。
		Attribute objectClass = new BasicAttribute("objectClass");
		entry.put(objectClass);
		String[] objectClasses = ctx.getObjectClasses();
		for (int i = 0; i < objectClasses.length; i++) {
			objectClass.add(objectClasses[i]);
		}
		// 属性を設定します。
		Set keySet = ctx.getArgKeySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String argName = String.valueOf(iter.next());
			Object argValue = ctx.getArg(argName);
			Class argClass = ctx.getArgType(argName);
			if (argName.equals("#dto")) {
				// 引数がDTOの場合、DTOに定義されたフィールドを
				// 作成するエントリの属性とみなします。
				int size = beanMetaData.getPropertyTypeSize();
				for (int i = 0; i < size; i++) {
					PropertyType pt = beanMetaData.getPropertyType(i);
					if (pt.isPersistent() == false) {
						continue;
					}
					PropertyDesc pd = pt.getPropertyDesc();
					String attributeName = pt.getColumnName();
					Attribute addAttribute =
						createAttribute(
							attributeName,
							pd.getValue(argValue),
							pd.getPropertyType());
					if (addAttribute != null) {
						entry.put(addAttribute);
					}
				}
			} else {
				// 引数がDTOではない場合、
				// 引数を作成エントリの属性とみなします。
				Attribute addAttribute =
					createAttribute(argName, argValue, argClass);
				if (addAttribute != null) {
					entry.put(addAttribute);
				}
			}

		}
		return entry;
	}

	private Attribute createAttribute(String attributeName, Object value,
			Class valueClass) {
		// 属性を設定します。
		DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory =
			ctx.getDirectoryAttributeHandlerFactory();
		AttributeHandler attributeHandler =
			directoryAttributeHandlerFactory.getAttributeHandler(attributeName);
		Attribute addAttribute =
			attributeHandler.getAddAttribute(
				property,
				attributeName,
				value,
				valueClass);
		return addAttribute;
	}

}
