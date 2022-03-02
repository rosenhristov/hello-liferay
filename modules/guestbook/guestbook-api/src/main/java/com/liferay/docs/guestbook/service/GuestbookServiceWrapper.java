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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link GuestbookService}.
 *
 * @author Rosen Hristov
 * @see GuestbookService
 * @generated
 */
public class GuestbookServiceWrapper implements GuestbookService, ServiceWrapper<GuestbookService> {

	private GuestbookService guestbookService;

	public GuestbookServiceWrapper(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return guestbookService.getOSGiServiceIdentifier();
	}

	@Override
	public GuestbookService getWrappedService() {
		return guestbookService;
	}

	@Override
	public void setWrappedService(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}

}