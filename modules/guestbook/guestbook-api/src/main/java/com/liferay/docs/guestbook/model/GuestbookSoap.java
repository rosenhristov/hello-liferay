/**
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.docs.guestbook.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.docs.guestbook.service.http.GuestbookServiceSoap}.
 *
 * @author Rosen Hristov
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class GuestbookSoap implements Serializable {

	private long mvccVersion;
	private String uuid;
	private long guestbookId;
	private String name;
	private long groupId;
	private long companyId;
	private long userId;
	private String userName;
	private Date createDate;
	private Date modifiedDate;

	public static GuestbookSoap toSoapModel(Guestbook model) {
		GuestbookSoap soapModel = new GuestbookSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setGuestbookId(model.getGuestbookId());
		soapModel.setName(model.getName());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static GuestbookSoap[] toSoapModels(Guestbook[] models) {
		GuestbookSoap[] soapModels = new GuestbookSoap[models.length];
		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}
		return soapModels;
	}

	public static GuestbookSoap[][] toSoapModels(Guestbook[][] models) {
		GuestbookSoap[][] soapModels;
		if (models.length > 0) {
			soapModels = new GuestbookSoap[models.length][models[0].length];
		} else {
			soapModels = new GuestbookSoap[0][0];
		}
		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}
		return soapModels;
	}

	public static GuestbookSoap[] toSoapModels(List<Guestbook> models) {
		List<GuestbookSoap> soapModels = new ArrayList<>(models.size());
		for (Guestbook model : models) {
			soapModels.add(toSoapModel(model));
		}
		return soapModels.toArray(new GuestbookSoap[soapModels.size()]);
	}

	public GuestbookSoap() {
	}

	public long getPrimaryKey() {
		return guestbookId;
	}

	public void setPrimaryKey(long pk) {
		setGuestbookId(pk);
	}

	public long getMvccVersion() {
		return mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getGuestbookId() {
		return guestbookId;
	}

	public void setGuestbookId(long guestbookId) {
		this.guestbookId = guestbookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}