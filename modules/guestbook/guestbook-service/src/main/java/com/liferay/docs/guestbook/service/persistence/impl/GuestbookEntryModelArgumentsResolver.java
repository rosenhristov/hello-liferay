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

package com.liferay.docs.guestbook.service.persistence.impl;

import com.liferay.docs.guestbook.model.GuestbookEntryTable;
import com.liferay.docs.guestbook.model.impl.GuestbookEntryImpl;
import com.liferay.docs.guestbook.model.impl.GuestbookEntryModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

import static com.liferay.docs.guestbook.service.persistence.impl.GuestbookEntryPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION;
import static java.util.Objects.isNull;

/**
 * The arguments resolver class for retrieving value from GuestbookEntry.
 *
 * @author Rosen Hristov
 * @generated
 */
@Component(
	immediate = true,
	service = {
		GuestbookEntryModelArgumentsResolver.class, ArgumentsResolver.class
	}
)
public class GuestbookEntryModelArgumentsResolver implements ArgumentsResolver {

	@Override
	public Object[] getArguments(FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn, boolean original) {
		String[] columnNames = finderPath.getColumnNames();
		if ((isNull(columnNames)) || (columnNames.length == 0)) {
			return baseModel.isNew() ? new Object[0] : null;
		}
		GuestbookEntryModelImpl guestbookEntryModelImpl = (GuestbookEntryModelImpl) baseModel;
		long columnBitmask = guestbookEntryModelImpl.getColumnBitmask();
		if (!checkColumn || (columnBitmask == 0)) {
			return getValue(guestbookEntryModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = finderPathColumnBitmasksCache.get(finderPath);
		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				//FIXME fix this!
				finderPathColumnBitmask |= guestbookEntryModelImpl.getColumnBitmask();
			}
			if (finderPath.isBaseModelResult() 
					&& (FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION == finderPath.getCacheName())) {
				finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
			}
			finderPathColumnBitmasksCache.put(finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return getValue(guestbookEntryModelImpl, columnNames, original);
		}
		
		return null;
	}

	@Override
	public String getClassName() {
		return GuestbookEntryImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return GuestbookEntryTable.INSTANCE.getTableName();
	}

	private static Object[] getValue(
		GuestbookEntryModelImpl guestbookEntryModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = guestbookEntryModelImpl.getColumnOriginalValue(columnName);
			}
			else {
				arguments[i] = guestbookEntryModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

	private static final long _ORDER_BY_COLUMNS_BITMASK;

	static {
		long orderByColumnsBitmask = 0;

		orderByColumnsBitmask |= GuestbookEntryModelImpl.getColumnBitmask("createDate");

		_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
	}

}