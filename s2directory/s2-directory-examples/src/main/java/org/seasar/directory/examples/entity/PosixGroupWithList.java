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
package org.seasar.directory.examples.entity;

import java.io.Serializable;
import java.util.List;

public class PosixGroupWithList implements Serializable {
	private static final long serialVersionUID = 1L;
	// public static final String OBJECTCLASS = "posixGroup";
	private String dn;
	private String cn;
	private String gidNumber;
	private String userPassword;
	private List memberUid;
	private String description;

	public PosixGroupWithList() {
		super();
	}

	public PosixGroupWithList(String cn, String gidNumber) {
		super();
		this.cn = cn;
		this.gidNumber = gidNumber;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCn() {
		return cn;
	}

	public void setGidNumber(String gidNumber) {
		this.gidNumber = gidNumber;
	}

	public String getGidNumber() {
		return gidNumber;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setMemberUid(List memberUid) {
		this.memberUid = memberUid;
	}

	public List getMemberUid() {
		return memberUid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("DN: ");
		buf.append("dn=").append(dn).append(", ");
		buf.append("MUST: ");
		buf.append("cn=").append(cn).append(", ");
		buf.append("gidNumber=").append(gidNumber).append(", ");
		buf.append("MAY: ");
		buf.append("userPassword=").append(userPassword).append(", ");
		buf.append("memberUid=").append(memberUid).append(", ");
		buf.append("description=").append(description);
		return buf.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}