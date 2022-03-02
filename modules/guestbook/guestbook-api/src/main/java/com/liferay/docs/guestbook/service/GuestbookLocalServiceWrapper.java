/**
 *
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

package com.liferay.docs.guestbook.service;

import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;
import java.util.List;

/**
 * Provides a wrapper for {@link GuestbookLocalService}.
 *
 * @author Rosen Hristov
 * @see GuestbookLocalService
 * @generated
 */
public class GuestbookLocalServiceWrapper implements GuestbookLocalService, ServiceWrapper<GuestbookLocalService> {

	private GuestbookLocalService guestbookLocalService;

	public GuestbookLocalServiceWrapper(GuestbookLocalService guestbookLocalService) {
		this.guestbookLocalService = guestbookLocalService;
	}

	/**
	 * Adds the guestbook to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GuestbookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param guestbook the guestbook
	 * @return the guestbook that was added
	 */
	@Override
	public com.liferay.docs.guestbook.model.Guestbook addGuestbook(Guestbook guestbook) {
		return guestbookLocalService.addGuestbook(guestbook);
	}

	@Override
	public Guestbook addGuestbook(long userId, String name, ServiceContext serviceContext)
									throws PortalException {
		return guestbookLocalService.addGuestbook(userId, name, serviceContext);
	}

	/**
	 * Creates a new guestbook with the primary key. Does not add the guestbook to the database.
	 *
	 * @param guestbookId the primary key for the new guestbook
	 * @return the new guestbook
	 */
	@Override
	public com.liferay.docs.guestbook.model.Guestbook createGuestbook(long guestbookId) {
		return guestbookLocalService.createGuestbook(guestbookId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj) throws PortalException {
		return guestbookLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the guestbook from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GuestbookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param guestbook the guestbook
	 * @return the guestbook that was removed
	 */
	@Override
	public com.liferay.docs.guestbook.model.Guestbook deleteGuestbook(Guestbook guestbook) {
		return guestbookLocalService.deleteGuestbook(guestbook);
	}

	/**
	 * Deletes the guestbook with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GuestbookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param guestbookId the primary key of the guestbook
	 * @return the guestbook that was removed
	 * @throws PortalException if a guestbook with the primary key could not be found
	 */
	@Override
	public com.liferay.docs.guestbook.model.Guestbook deleteGuestbook(long guestbookId) throws PortalException {
		return guestbookLocalService.deleteGuestbook(guestbookId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(PersistedModel persistedModel)
			throws PortalException {
		return guestbookLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return guestbookLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		return guestbookLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return guestbookLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return guestbookLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.docs.guestbook.model.impl.GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(DynamicQuery dynamicQuery, int start, int end) {
		return guestbookLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.docs.guestbook.model.impl.GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
											  OrderByComparator<T> orderByComparator) {
		return guestbookLocalService.dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return guestbookLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery, Projection projection) {
		return guestbookLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.docs.guestbook.model.Guestbook fetchGuestbook(long guestbookId) {
		return guestbookLocalService.fetchGuestbook(guestbookId);
	}

	/**
	 * Returns the guestbook matching the UUID and group.
	 *
	 * @param uuid the guestbook's UUID
	 * @param groupId the primary key of the group
	 * @return the matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public com.liferay.docs.guestbook.model.Guestbook fetchGuestbookByUuidAndGroupId(String uuid, long groupId) {
		return guestbookLocalService.fetchGuestbookByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return guestbookLocalService.getActionableDynamicQuery();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(PortletDataContext portletDataContext) {
		return guestbookLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns the guestbook with the primary key.
	 *
	 * @param guestbookId the primary key of the guestbook
	 * @return the guestbook
	 * @throws PortalException if a guestbook with the primary key could not be found
	 */
	@Override
	public com.liferay.docs.guestbook.model.Guestbook getGuestbook(long guestbookId) throws PortalException {
		return guestbookLocalService.getGuestbook(guestbookId);
	}

	/**
	 * Returns the guestbook matching the UUID and group.
	 *
	 * @param uuid the guestbook's UUID
	 * @param groupId the primary key of the group
	 * @return the matching guestbook
	 * @throws PortalException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook getGuestbookByUuidAndGroupId(String uuid, long groupId) throws PortalException {
		return guestbookLocalService.getGuestbookByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the guestbooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.docs.guestbook.model.impl.GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @return the range of guestbooks
	 */
	@Override
	public List<Guestbook> getGuestbooks(int start, int end) {
		return guestbookLocalService.getGuestbooks(start, end);
	}

	/**
	 * Returns all the guestbooks matching the UUID and company.
	 *
	 * @param uuid the UUID of the guestbooks
	 * @param companyId the primary key of the company
	 * @return the matching guestbooks, or an empty list if no matches were found
	 */
	@Override
	public List<com.liferay.docs.guestbook.model.Guestbook>		getGuestbooksByUuidAndCompanyId(String uuid, long companyId) {
		return guestbookLocalService.getGuestbooksByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of guestbooks matching the UUID and company.
	 *
	 * @param uuid the UUID of the guestbooks
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching guestbooks, or an empty list if no matches were found
	 */
	@Override
	public List<Guestbook> getGuestbooksByUuidAndCompanyId(String uuid, long companyId, int start, int end, OrderByComparator<Guestbook> orderByComparator) {
		return guestbookLocalService.getGuestbooksByUuidAndCompanyId(uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of guestbooks.
	 *
	 * @return the number of guestbooks
	 */
	@Override
	public int getGuestbooksCount() {
		return guestbookLocalService.getGuestbooksCount();
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return guestbookLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return guestbookLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj) throws PortalException {
		return guestbookLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the guestbook in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GuestbookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param guestbook the guestbook
	 * @return the guestbook that was updated
	 */
	@Override
	public Guestbook updateGuestbook(Guestbook guestbook) {
		return guestbookLocalService.updateGuestbook(guestbook);
	}

	@Override
	public List<Guestbook> getGuestbooks(long groupId) {
		return getWrappedService().getGuestbooks(groupId);
	}

	@Override
	public GuestbookLocalService getWrappedService() {
		return guestbookLocalService;
	}

	@Override
	public void setWrappedService(GuestbookLocalService guestbookLocalService) {
		this.guestbookLocalService = guestbookLocalService;
	}

}