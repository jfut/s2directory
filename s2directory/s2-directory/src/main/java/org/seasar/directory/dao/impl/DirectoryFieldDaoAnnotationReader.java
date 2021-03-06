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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.util.DaoUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * フィールドアノテーションを読み込むクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryFieldDaoAnnotationReader implements
		DirectoryDaoAnnotationReader {

	/** BEANアノテーションの設定名 */
	public String BEAN = "BEAN";

	/** オブジェクトクラスアノテーション */
	public String OBJECTCLASSES = "OBJECTCLASSES";

	/** ARGSアノテーションの設定名 */
	public String ARGS_SUFFIX = "_ARGS";

	/** FILTERアノテーションの設定名 */
	public String FILTER_SUFFIX = "_FILTER";

	/** QUERYアノテーションの設定名 */
	public String QUERY_SUFFIX = "_QUERY";

	/** 永続化対象にしない属性の設定名 */
	public String NO_PERSISTENT_PROPS_SUFFIX = "_NO_PERSISTENT_PROPS";

	/** この属性だけ永続化する属性の設定名 */
	public String PERSISTENT_PROPS_SUFFIX = "_PERSISTENT_PROPS";

	/** Daoクラスのメタ情報 */
	protected BeanDesc daoBeanDesc;

	/** 基底オブジェクトクラス */
	private final static String BASE_OBJECTCLASS = "top";

	/**
	 * 指定されたメタ情報を持つインスタンスを作成します。
	 * 
	 * @param daoBeanDesc
	 */
	public DirectoryFieldDaoAnnotationReader(BeanDesc daoBeanDesc) {
		this.daoBeanDesc = daoBeanDesc;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getArgNames(Method method) {
		String methodName = method.getName();
		String argsKey = methodName + ARGS_SUFFIX;
		if (daoBeanDesc.hasField(argsKey)) {
			Field argNamesField = daoBeanDesc.getField(argsKey);
			String argNames = (String)FieldUtil.get(argNamesField, null);
			return StringUtil.split(argNames, " ,");
		} else {
			return new String[0];
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getQuery(Method method) {
		String methodName = method.getName();
		String key = methodName + QUERY_SUFFIX;
		if (daoBeanDesc.hasField(key)) {
			Field queryField = daoBeanDesc.getField(key);
			return (String)FieldUtil.get(queryField, null);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getBeanClass() {
		Field beanField = daoBeanDesc.getField(BEAN);
		return (Class)FieldUtil.get(beanField, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getObjectClasses(String[] beanObjectClasses) {
		// Dao インタフェースに OBJECTCLASSES フィールドアノテーションがある場合
		String[] objectClasses = getObjectClassesFromAnnotation();
		if (objectClasses != null) {
			return objectClasses;
		}

		// Dao インタフェースに OBJECTCLASSES フィールドアノテーションがない場合
		objectClasses = getObjectClassesFromBeanAnnotation();
		if (objectClasses != null) {
			return objectClasses;
		}
		if (beanObjectClasses != null) {
			// ビーンクラスにOBJECTCLASSESアノテーションがある場合
			objectClasses = beanObjectClasses;
		} else {
			// ビーンクラスにOBJECTCLASSESアノテーションがない場合
			objectClasses = new String[1];
			objectClasses[0] = DaoUtil.getSimpleClassName(this.getBeanClass());
		}
		return setupObjectClass(objectClasses);
	}

	/**
	 * Dao インタフェースの OBJECTCLASSES フィールドアノテーションから
	 * オブジェクトクラスアノテーションの値の配列を返します。
	 * 存在しない場合は、null を返します。
	 * 
	 * @return オブジェクトクラスアノテーションの値の配列
	 */
	protected String[] getObjectClassesFromAnnotation() {
		if (daoBeanDesc.hasField(OBJECTCLASSES)) {
			Field queryField = daoBeanDesc.getField(OBJECTCLASSES);
			String objectClassNames = (String)FieldUtil.get(queryField, null);
			return setupObjectClass(objectClassNames.split(","));
		}
		return null;
	}

	/**
	 * Dao インタフェースに定義された Bean クラスの OBJECTCLASSES
	 * フィールドアノテーションからオブジェクトクラスアノテーションの値の配列を返します。
	 * Bean クラスは親クラスを辿って探します。
	 * 存在しない場合は、null を返します。
	 * 
	 * @return オブジェクトクラスアノテーションの値の配列
	 */
	protected String[] getObjectClassesFromBeanAnnotation() {
		Class beanClass = getBeanClass();
		for (Class superClass = beanClass; superClass != Object.class; superClass =
			superClass.getSuperclass()) {
			BeanDesc daoBeanBeanDesc = BeanDescFactory.getBeanDesc(superClass);
			if (daoBeanBeanDesc.hasField(OBJECTCLASSES)) {
				Field queryField = daoBeanBeanDesc.getField(OBJECTCLASSES);
				String objectClassNames =
					(String)FieldUtil.get(queryField, null);
				return setupObjectClass(objectClassNames.split(","));
			}
		}
		return null;
	}

	/**
	 * オブジェクトクラスへ top が無い場合に追加して返します。
	 * 
	 * @param objectClasses
	 *            オブジェクトクラスアノテーションの値の配列
	 * @return セットアップ済みのオブジェクトクラスアノテーションの値の配列
	 */
	protected String[] setupObjectClass(String[] objectClasses) {
		if (objectClasses == null) {
			return objectClasses;
		}
		for (int i = 0; i < objectClasses.length; i++) {
			objectClasses[i] = objectClasses[i].trim();
		}
		return addObjectClass(objectClasses, BASE_OBJECTCLASS);
	}

	/**
	 * 指定されたオブジェクトクラス名を持っていない場合、追加します。
	 * 
	 * @param objectClasses
	 *            オブジェクトクラスアノテーションの値の配列
	 * @param objectClass
	 *            追加するオブジェクトクラス名
	 * @return オブジェクトクラスアノテーションの値の配列
	 */
	protected String[] addObjectClass(String[] objectClasses, String objectClass) {
		if (ArrayUtil.contains(objectClasses, objectClass)) {
			return objectClasses;
		}
		return (String[])ArrayUtil.add(objectClasses, objectClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getNoPersistentProps(Method method) {
		String methodName = method.getName();
		return getProps(methodName, methodName + NO_PERSISTENT_PROPS_SUFFIX);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getPersistentProps(Method method) {
		String methodName = method.getName();
		return getProps(methodName, methodName + PERSISTENT_PROPS_SUFFIX);
	}

	/**
	 * 指定された関数用のフィールドを取得します。
	 * 
	 * @param methodName
	 * @param fieldName
	 * @return
	 */
	private String[] getProps(String methodName, String fieldName) {
		if (daoBeanDesc.hasField(fieldName)) {
			Field field = daoBeanDesc.getField(fieldName);
			String s = (String)FieldUtil.get(field, null);
			return StringUtil.split(s, ", ");
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getFilter(Method method) {
		String methodName = method.getName();
		String key = methodName + FILTER_SUFFIX;
		if (daoBeanDesc.hasField(key)) {
			Field queryField = daoBeanDesc.getField(key);
			return (String)FieldUtil.get(queryField, null);
		}
		return null;
	}

}
