/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.framework.container.hotdeploy.creator;

import org.seasar.framework.container.autoregister.ComponentCustomizer;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * HotDeploy時にDirecotryDaoのインスタンスを管理するオンデマンドクリエータです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoOndemandCreator extends SinglePackageOndemandCreator {
	/**
	 * インスタンスを作成します。
	 * 
	 * @param namingConvention ネーミングコンバージョン
	 */
	public DirectoryDaoOndemandCreator(NamingConvention namingConvention) {
		super(namingConvention);
		setMiddlePackageName("directory" + namingConvention.getDaoPackageName());
		setNameSuffix(namingConvention.getDaoSuffix());
		setInstanceDef(InstanceDefFactory.PROTOTYPE);
	}

	/**
	 * DirectoryDao用カスタマイザを取得します。
	 * 
	 * @return カスタマイザ
	 */
	public ComponentCustomizer getDirectoryDaoCustomizer() {
		return getCustomizer();
	}

	/**
	 * DirectoryDao用スタマイザを設定します。
	 * 
	 * @param customizer カスタマイザ
	 */
	public void setDirectoryDaoCustomizer(ComponentCustomizer customizer) {
		setCustomizer(customizer);
	}
}