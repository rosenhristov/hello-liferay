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
 * This class is used by SOAP remote services, specifically {@link com.liferay.docs.guestbook.service.http.GuestbookEntryServiceSoap}.
 *
 * @author Rosen Hristov
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class GuestbookEntrySoap implements Serializable {

	private long 	mvccVersion;
	private String 	uuid;
	private long 	entryId;
	private String 	name;
	private String 	email;
	private String 	message;
	private long 	guestbookId;
	private long 	groupId;
	private long 	companyId;
	private long 	userId;
	private String 	userName;
	private Date 	createDate;
	private Date 	modifiedDate;
	private int 	status;
	private long 	statusByUserId;
	private String 	statusByUserName;
	private Date 	statusDate;

	public static GuestbookEntrySoap toSoapModel(GuestbookEntry model) {
		GuestbookEntrySoap soapModel = new GuestbookEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setName(model.getName());
		soapModel.setEmail(model.getEmail());
		soapModel.setMessage(model.getMessage());
		soapModel.setGuestbookId(model.getGuestbookId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static GuestbookEntrySoap[] toSoapModels(GuestbookEntry[] models) {
		GuestbookEntrySoap[] soapModels = new GuestbookEntrySoap[models.length];
		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}
		return soapModels;
	}

	public static GuestbookEntrySoap[][] toSoapModels(GuestbookEntry[][] models) {
		GuestbookEntrySoap[][] soapModels;
		if (models.length > 0) {
			soapModels = new GuestbookEntrySoap[models.length][models[0].length];
		} else {
			soapModels = new GuestbookEntrySoap[0][0];
		}
		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}
		return soapModels;
	}

	public static GuestbookEntrySoap[] toSoapModels(List<GuestbookEntry> models) {
		List<GuestbookEntrySoap> soapModels = new ArrayList<>(models.size());
		for (GuestbookEntry model : models) {
			soapModels.add(toSoapModel(model));
		}
		return soapModels.toArray(new GuestbookEntrySoap[soapModels.size()]);
	}

	public GuestbookEntrySoap() {
	}

	public long getPrimaryKey() {
		return entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
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

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getGuestbookId() {
		return guestbookId;
	}

	public void setGuestbookId(long guestbookId) {
		this.guestbookId = guestbookId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getStatusByUserId() {
		return statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		this.statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		this.statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
}