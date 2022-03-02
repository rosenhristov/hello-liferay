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

import com.liferay.docs.guestbook.exception.NoSuchGuestbookException;
import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.model.GuestbookTable;
import com.liferay.docs.guestbook.model.impl.GuestbookImpl;
import com.liferay.docs.guestbook.model.impl.GuestbookModelImpl;
import com.liferay.docs.guestbook.service.persistence.GuestbookPersistence;
import com.liferay.docs.guestbook.service.persistence.GuestbookUtil;
import com.liferay.docs.guestbook.service.persistence.impl.constants.GBPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * The persistence implementation for the guestbook service.
 *
 * NOTE FOR DEVELOPERS:
 * Never modify or reference this class directly.
 * Always use <code>GuestbookUtil</code> to access the guestbook persistence.
 * Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Rosen Hristov
 * @generated
 */
@Component(service = {GuestbookPersistence.class, BasePersistence.class})
public class GuestbookPersistenceImpl extends BasePersistenceImpl<Guestbook> implements GuestbookPersistence {

	public static final String FINDER_CLASS_NAME_ENTITY = GuestbookImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY + ".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY + ".List2";

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "guestbook.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(guestbook.uuid IS NULL OR guestbook.uuid = '')";

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "guestbook.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(guestbook.uuid IS NULL OR guestbook.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "guestbook.groupId = ?";

	private static final String FINDER_COLUMN_UUID_C_UUID_2 = "guestbook.uuid = ? AND ";
	private static final String FINDER_COLUMN_UUID_C_UUID_3 = "(guestbook.uuid IS NULL OR guestbook.uuid = '') AND ";
	private static final String FINDER_COLUMN_UUID_C_COMPANYID_2 = "guestbook.companyId = ?";
	private static final String FINDER_COLUMN_GROUPID_GROUPID_2 = "guestbook.groupId = ?";

	private static final String SQL_SELECT_GUESTBOOK = "SELECT guestbook FROM Guestbook guestbook";
	private static final String SQL_SELECT_GUESTBOOK_WHERE = "SELECT guestbook FROM Guestbook guestbook WHERE ";
	private static final String SQL_COUNT_GUESTBOOK = "SELECT COUNT(guestbook) FROM Guestbook guestbook";
	private static final String SQL_COUNT_GUESTBOOK_WHERE = "SELECT COUNT(guestbook) FROM Guestbook guestbook WHERE ";
	private static final String ORDER_BY_ENTITY_ALIAS = "guestbook.";
	private static final String NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Guestbook exists with the primary key ";
	private static final String NO_SUCH_ENTITY_WITH_KEY = "No Guestbook exists with the key {";

	private FinderPath finderPathWithPaginationFindAll;
	private FinderPath finderPathWithoutPaginationFindAll;
	private FinderPath finderPathCountAll;
	private FinderPath finderPathWithPaginationFindByUuid;
	private FinderPath finderPathWithoutPaginationFindByUuid;
	private FinderPath finderPathCountByUuid;

	private FinderPath finderPathFetchByUUID_G;
	private FinderPath finderPathCountByUUID_G;

	private FinderPath finderPathWithPaginationFindByUuid_C;
	private FinderPath finderPathWithoutPaginationFindByUuid_C;
	private FinderPath finderPathCountByUuid_C;

	private FinderPath finderPathWithPaginationFindByGroupId;
	private FinderPath finderPathWithoutPaginationFindByGroupId;
	private FinderPath finderPathCountByGroupId;

	private int valueObjectFinderCacheListThreshold;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	@Reference
	private GuestbookModelArgumentsResolver guestbookModelArgumentsResolver;

	private static final Log log = LogFactoryUtil.getLog(GuestbookPersistenceImpl.class);
	private static final Set<String> badColumnNames = SetUtil.fromArray(new String[] {"uuid"});

	public GuestbookPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<>();
		dbColumnNames.put("uuid", "uuid_");
		setDBColumnNames(dbColumnNames);
		setModelClass(Guestbook.class);
		setModelImplClass(GuestbookImpl.class);
		setModelPKClass(long.class);
		setTable(GuestbookTable.INSTANCE);
	}

	/**
	 * Initializes the guestbook persistence.
	 */
	@Activate
	public void activate() {
		valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
				PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		finderPathWithPaginationFindAll = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
				new String[0], true);

		finderPathWithoutPaginationFindAll = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
				new String[0], true);

		finderPathCountAll = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0], new String[0], false);

		finderPathWithPaginationFindByUuid = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
				new String[] {String.class.getName(), Integer.class.getName(),
						      Integer.class.getName(), OrderByComparator.class.getName()},
				new String[] {"uuid_"}, true);

		finderPathWithoutPaginationFindByUuid = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
				new String[] {String.class.getName()}, new String[] {"uuid_"},
				true);

		finderPathCountByUuid = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
				new String[] {String.class.getName()}, new String[] {"uuid_"},
				false);

		finderPathFetchByUUID_G = new FinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"uuid_", "groupId"}, true);

		finderPathCountByUUID_G = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"uuid_", "groupId"}, false);

		finderPathWithPaginationFindByUuid_C = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
				new String[] {String.class.getName(), Long.class.getName(), Integer.class.getName(), Integer.class.getName(),
						      OrderByComparator.class.getName()},
				new String[] {"uuid_", "companyId"}, true);

		finderPathWithoutPaginationFindByUuid_C = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"uuid_", "companyId"}, true);

		finderPathCountByUuid_C = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"uuid_", "companyId"}, false);

		finderPathWithPaginationFindByGroupId = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
				new String[] {Long.class.getName(), Integer.class.getName(), Integer.class.getName(),
						      OrderByComparator.class.getName()},
				new String[] {"groupId"}, true);

		finderPathWithoutPaginationFindByGroupId = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
				new String[] {Long.class.getName()}, new String[] {"groupId"},
				true);

		finderPathCountByGroupId = new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
				new String[] {Long.class.getName()}, new String[] {"groupId"},
				false);

		setGuestbookUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		setGuestbookUtilPersistence(null);
		entityCache.removeCache(GuestbookImpl.class.getName());
	}

	/**
	 * Returns all the guestbooks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the guestbooks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @return the range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the guestbooks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid(String uuid, int start, int end,
									  OrderByComparator<Guestbook> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the guestbooks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid(String uuid, int start, int end,
									  OrderByComparator<Guestbook> orderByComparator, boolean useFinderCache) {
		uuid = Objects.toString(uuid, "");
		FinderPath finderPath = null;
		Object[] finderArgs = null;
		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)
				&& (isNull(orderByComparator))) {
			if (useFinderCache) {
				finderPath = finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		} else if (useFinderCache) {
			finderPath = finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}
		List<Guestbook> list = null;
		if (useFinderCache) {
			list = (List<Guestbook>) finderCache.getResult(finderPath, finderArgs);
			if (nonNull(list) && !list.isEmpty()) {
				for (Guestbook guestbook : list) {
					if (!uuid.equals(guestbook.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (isNull(list)) {
			StringBundler sb;
			if (nonNull(orderByComparator)) {
				sb = new StringBundler(3 + (orderByComparator.getOrderByFields().length * 2));
			} else {
				sb = new StringBundler(3);
			}
			sb.append(SQL_SELECT_GUESTBOOK_WHERE);
			boolean bindUuid = false;
			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			} else {
				bindUuid = true;
				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}
			if (nonNull(orderByComparator)) {
				appendOrderByComparator(sb, ORDER_BY_ENTITY_ALIAS, orderByComparator);
			} else {
				sb.append(GuestbookModelImpl.ORDER_BY_JPQL);
			}
			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				if (bindUuid) {
					queryPos.add(uuid);
				}
				list = (List<Guestbook>) QueryUtil.list(query, getDialect(), start, end);
				cacheResult(list);
				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}
		return list;
	}

	/**
	 * Returns the first guestbook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching guestbook
	 * @throws NoSuchGuestbookException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook findByUuid_First(String uuid, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByUuid_First(uuid, orderByComparator);
		if (nonNull(guestbook)) {
			return guestbook;
		}

		StringBundler sb = new StringBundler(4);
		sb.append(NO_SUCH_ENTITY_WITH_KEY);
		sb.append("uuid=");
		sb.append(uuid);
		sb.append("}");

		throw new NoSuchGuestbookException(sb.toString());
	}

	/**
	 * Returns the first guestbook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByUuid_First(String uuid, OrderByComparator<Guestbook> orderByComparator) {
		List<Guestbook> list = findByUuid(uuid, 0, 1, orderByComparator);

		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * Returns the last guestbook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching guestbook
	 * @throws NoSuchGuestbookException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook findByUuid_Last(String uuid, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByUuid_Last(uuid, orderByComparator);
		if (nonNull(guestbook)) {
			return guestbook;
		}

		StringBundler sb = new StringBundler(4);
		sb.append(NO_SUCH_ENTITY_WITH_KEY);
		sb.append("uuid=");
		sb.append(uuid);
		sb.append("}");

		throw new NoSuchGuestbookException(sb.toString());
	}

	/**
	 * Returns the last guestbook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByUuid_Last(String uuid, OrderByComparator<Guestbook> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) return null;

		List<Guestbook> list = findByUuid(uuid, count - 1, count, orderByComparator);

		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * Returns the guestbooks before and after the current guestbook in the ordered set where uuid = &#63;.
	 *
	 * @param guestbookId the primary key of the current guestbook
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next guestbook
	 * @throws NoSuchGuestbookException if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook[] findByUuid_PrevAndNext(long guestbookId, String uuid, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		uuid = Objects.toString(uuid, "");
		Guestbook guestbook = findByPrimaryKey(guestbookId);
		Session session = null;
		try {
			session = openSession();
			Guestbook[] array = new GuestbookImpl[3];
			array[0] = getByUuid_PrevAndNext(session, guestbook, uuid, orderByComparator, true);
			array[1] = guestbook;
			array[2] = getByUuid_PrevAndNext(session, guestbook, uuid, orderByComparator, false);

			return array;

		} catch (Exception exception) {
			throw processException(exception);
		} finally {
			closeSession(session);
		}
	}

	protected Guestbook getByUuid_PrevAndNext(Session session, Guestbook guestbook, String uuid,
											  OrderByComparator<Guestbook> orderByComparator, boolean previous) {

		StringBundler sb;
		if (nonNull(orderByComparator)) {
			sb = new StringBundler(4 + (orderByComparator.getOrderByConditionFields().length * 3)
										+ (orderByComparator.getOrderByFields().length * 3));
		} else {
			sb = new StringBundler(3);
		}
		sb.append(SQL_SELECT_GUESTBOOK_WHERE);
		boolean bindUuid = false;
		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		} else {
			bindUuid = true;
			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (nonNull(orderByComparator)) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();
			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);
				if (orderByConditionFields.length > i + 1) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					} else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				} else if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
				} else {
					sb.append(WHERE_LESSER_THAN);
				}
			}
			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();
			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);
				if (orderByFields.length > i + 1) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					} else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				} else if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
				} else {
						sb.append(ORDER_BY_DESC);
				}
			}
		} else {
			sb.append(GuestbookModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();
		Query query = session.createQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(2);
		QueryPos queryPos = QueryPos.getInstance(query);
		if (bindUuid) {
			queryPos.add(uuid);
		}
		if (nonNull(orderByComparator)) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(guestbook)) {
				queryPos.add(orderByConditionValue);
			}
		}
		List<Guestbook> list = query.list();

		return list.size() == 2 ? list.get(1) : null;
	}

	/**
	 * Removes all the guestbooks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Guestbook guestbook : findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(guestbook);
		}
	}

	/**
	 * Returns the number of guestbooks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching guestbooks
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");
		FinderPath finderPath = finderPathCountByUuid;
		Object[] finderArgs = new Object[] {uuid};
		Long count = (Long)finderCache.getResult(finderPath, finderArgs);
		if (isNull(count)) {
			StringBundler sb = new StringBundler(2);
			sb.append(SQL_COUNT_GUESTBOOK_WHERE);
			boolean bindUuid = false;
			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			} else {
				bindUuid = true;
				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}
			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				if (bindUuid) {
					queryPos.add(uuid);
				}
				count = (Long)query.uniqueResult();
				finderCache.putResult(finderPath, finderArgs, count);
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the guestbook where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchGuestbookException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching guestbook
	 * @throws NoSuchGuestbookException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook findByUUID_G(String uuid, long groupId) throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByUUID_G(uuid, groupId);
		if (isNull(guestbook)) {
			StringBundler sb = new StringBundler(6);
			sb.append(NO_SUCH_ENTITY_WITH_KEY);
			sb.append("uuid=");
			sb.append(uuid);
			sb.append(", groupId=");
			sb.append(groupId);
			sb.append("}");
			if (log.isDebugEnabled()) {
				log.debug(sb.toString());
			}

			throw new NoSuchGuestbookException(sb.toString());
		}

		return guestbook;
	}

	/**
	 * Returns the guestbook where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the guestbook where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByUUID_G(String uuid, long groupId, boolean useFinderCache) {
		uuid = Objects.toString(uuid, "");
		Object[] finderArgs = null;
		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}
		Object result = null;
		if (useFinderCache) {
			result = finderCache.getResult(finderPathFetchByUUID_G, finderArgs);
		}
		if (result instanceof Guestbook) {
			Guestbook guestbook = (Guestbook)result;
			if (!Objects.equals(uuid, guestbook.getUuid())
					|| (groupId != guestbook.getGroupId())) {
				result = null;
			}
		}

		if (isNull(result)) {
			StringBundler sb = new StringBundler(4);
			sb.append(SQL_SELECT_GUESTBOOK_WHERE);
			boolean bindUuid = false;
			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			} else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);
			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				if (bindUuid) {
					queryPos.add(uuid);
				}
				queryPos.add(groupId);
				List<Guestbook> list = query.list();
				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(finderPathFetchByUUID_G, finderArgs, list);
					}
				} else {
					Guestbook guestbook = list.get(0);
					result = guestbook;
					cacheResult(guestbook);
				}
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		} else {
			return (Guestbook)result;
		}
	}

	/**
	 * Removes the guestbook where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the guestbook that was removed
	 */
	@Override
	public Guestbook removeByUUID_G(String uuid, long groupId) throws NoSuchGuestbookException {
		return remove(findByUUID_G(uuid, groupId));
	}

	/**
	 * Returns the number of guestbooks where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching guestbooks
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");
		FinderPath finderPath = finderPathCountByUUID_G;
		Object[] finderArgs = new Object[] {uuid, groupId};
		Long count = (Long)finderCache.getResult(finderPath, finderArgs);
		if (isNull(count)) {
			StringBundler sb = new StringBundler(3);
			sb.append(SQL_COUNT_GUESTBOOK_WHERE);
			boolean bindUuid = false;
			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			} else {
				bindUuid = true;
				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}
			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);
			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				if (bindUuid) {
					queryPos.add(uuid);
				}
				queryPos.add(groupId);
				count = (Long)query.uniqueResult();
				finderCache.putResult(finderPath, finderArgs, count);
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}
		return count.intValue();
	}
	/**
	 * Returns all the guestbooks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the guestbooks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @return the range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid_C(String uuid, long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the guestbooks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid_C(String uuid, long companyId, int start, int end,
										OrderByComparator<Guestbook> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the guestbooks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByUuid_C(String uuid, long companyId, int start, int end,
										OrderByComparator<Guestbook> orderByComparator, boolean useFinderCache) {
		uuid = Objects.toString(uuid, "");
		FinderPath finderPath = null;
		Object[] finderArgs = null;
		if (start == QueryUtil.ALL_POS && end == QueryUtil.ALL_POS && isNull(orderByComparator)) {
			if (useFinderCache) {
				finderPath = finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		} else if (useFinderCache) {
			finderPath = finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<Guestbook> list = null;
		if (useFinderCache) {
			list = (List<Guestbook>) finderCache.getResult(finderPath, finderArgs);
			if (nonNull(list) && !list.isEmpty()) {
				for (Guestbook guestbook : list) {
					if (!uuid.equals(guestbook.getUuid())
							|| companyId != guestbook.getCompanyId()) {
						list = null;
						break;
					}
				}
			}
		}

		if (isNull(list)) {
			StringBundler sb;
			if (nonNull(orderByComparator)) {
				sb = new StringBundler(4 + (orderByComparator.getOrderByFields().length * 2));
			} else {
				sb = new StringBundler(4);
			}

			sb.append(SQL_SELECT_GUESTBOOK_WHERE);
			boolean bindUuid = false;
			if (uuid.isEmpty()) {
				sb.append(FINDER_COLUMN_UUID_C_UUID_3);
			} else {
				bindUuid = true;
				sb.append(FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(FINDER_COLUMN_UUID_C_COMPANYID_2);
			if (nonNull(orderByComparator)) {
				appendOrderByComparator(sb, ORDER_BY_ENTITY_ALIAS, orderByComparator);
			} else {
				sb.append(GuestbookModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				if (bindUuid) {
					queryPos.add(uuid);
				}
				queryPos.add(companyId);
				list = (List<Guestbook>) QueryUtil.list(query, getDialect(), start, end);
				cacheResult(list);
				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first guestbook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching guestbook
	 * @throws NoSuchGuestbookException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook findByUuid_C_First(String uuid, long companyId, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByUuid_C_First(uuid, companyId, orderByComparator);
		if (nonNull(guestbook)) {
			return guestbook;
		}

		StringBundler sb = new StringBundler(6);
		sb.append(NO_SUCH_ENTITY_WITH_KEY);
		sb.append("uuid=");
		sb.append(uuid);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		throw new NoSuchGuestbookException(sb.toString());
	}

	/**
	 * Returns the first guestbook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByUuid_C_First(String uuid, long companyId, OrderByComparator<Guestbook> orderByComparator) {
		List<Guestbook> list = findByUuid_C(uuid, companyId, 0, 1, orderByComparator);

		return list.isEmpty() ? null :  list.get(0);
	}

	/**
	 * Returns the last guestbook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching guestbook
	 * @throws NoSuchGuestbookException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook findByUuid_C_Last(String uuid, long companyId, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (nonNull(guestbook)) return guestbook;

		StringBundler sb = new StringBundler(6);
		sb.append(NO_SUCH_ENTITY_WITH_KEY);
		sb.append("uuid=");
		sb.append(uuid);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		throw new NoSuchGuestbookException(sb.toString());
	}

	/**
	 * Returns the last guestbook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByUuid_C_Last(String uuid, long companyId, OrderByComparator<Guestbook> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);
		if (count == 0) {
			return null;
		}
		List<Guestbook> list = findByUuid_C(uuid, companyId, count - 1, count, orderByComparator);

		return list.isEmpty() ? null :  list.get(0);
	}

	/**
	 * Returns the guestbooks before and after the current guestbook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param guestbookId the primary key of the current guestbook
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next guestbook
	 * @throws NoSuchGuestbookException if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook[] findByUuid_C_PrevAndNext(long guestbookId, String uuid, long companyId,
												OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		uuid = Objects.toString(uuid, "");
		Guestbook guestbook = findByPrimaryKey(guestbookId);
		Session session = null;
		try {
			session = openSession();
			Guestbook[] array = new GuestbookImpl[3];
			array[0] = getByUuid_C_PrevAndNext(session, guestbook, uuid, companyId, orderByComparator, true);
			array[1] = guestbook;
			array[2] = getByUuid_C_PrevAndNext(session, guestbook, uuid, companyId, orderByComparator, false);

			return array;
		} catch (Exception exception) {
			throw processException(exception);
		} finally {
			closeSession(session);
		}
	}

	protected Guestbook getByUuid_C_PrevAndNext(Session session, Guestbook guestbook, String uuid, long companyId,
												OrderByComparator<Guestbook> orderByComparator, boolean previous) {
		StringBundler sb;
		if (nonNull(orderByComparator)) {
			sb = new StringBundler(5 + (orderByComparator.getOrderByConditionFields().length * 3)
					+ (orderByComparator.getOrderByFields().length * 3));
		} else {
			sb = new StringBundler(4);
		}
		sb.append(SQL_SELECT_GUESTBOOK_WHERE);
		boolean bindUuid = false;
		if (uuid.isEmpty()) {
			sb.append(FINDER_COLUMN_UUID_C_UUID_3);
		} else {
			bindUuid = true;
			sb.append(FINDER_COLUMN_UUID_C_UUID_2);
		}
		sb.append(FINDER_COLUMN_UUID_C_COMPANYID_2);
		if (nonNull(orderByComparator)) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();
			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}
			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);
				if (orderByConditionFields.length > i + 1) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					} else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
				} else {
						sb.append(WHERE_LESSER_THAN);
				}
			}
			sb.append(ORDER_BY_CLAUSE);
			String[] orderByFields = orderByComparator.getOrderByFields();
			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);
				if (orderByFields.length > i + 1) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					} else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				} else if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
				} else {
						sb.append(ORDER_BY_DESC);
				}
			}
		} else {
			sb.append(GuestbookModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();
		Query query = session.createQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(2);
		QueryPos queryPos = QueryPos.getInstance(query);
		if (bindUuid) {
			queryPos.add(uuid);
		}
		queryPos.add(companyId);
		if (nonNull(orderByComparator)) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(guestbook)) {
				queryPos.add(orderByConditionValue);
			}
		}
		List<Guestbook> list = query.list();

		return list.size() == 2 ? list.get(1) :  null;
	}

	/**
	 * Removes all the guestbooks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Guestbook guestbook : findByUuid_C(uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,	null)) {
			remove(guestbook);
		}
	}

	/**
	 * Returns the number of guestbooks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching guestbooks
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");
		FinderPath finderPath = finderPathCountByUuid_C;
		Object[] finderArgs = new Object[] {uuid, companyId};
		Long count = (Long)finderCache.getResult(finderPath, finderArgs);
		if (isNull(count)) {
			StringBundler sb = new StringBundler(3);
			sb.append(SQL_COUNT_GUESTBOOK_WHERE);
			boolean bindUuid = false;
			if (uuid.isEmpty()) {
				sb.append(FINDER_COLUMN_UUID_C_UUID_3);
			} else {
				bindUuid = true;
				sb.append(FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(FINDER_COLUMN_UUID_C_COMPANYID_2);
			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				if (bindUuid) {
					queryPos.add(uuid);
				}
				queryPos.add(companyId);
				count = (Long)query.uniqueResult();
				finderCache.putResult(finderPath, finderArgs, count);
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns all the guestbooks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching guestbooks
	 */
	@Override
	public List<Guestbook> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the guestbooks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @return the range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the guestbooks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByGroupId(long groupId, int start, int end, OrderByComparator<Guestbook> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the guestbooks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching guestbooks
	 */
	@Override
	public List<Guestbook> findByGroupId(long groupId, int start, int end,
										 OrderByComparator<Guestbook> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;
		if (start == QueryUtil.ALL_POS && end == QueryUtil.ALL_POS && isNull(orderByComparator)) {
			if (useFinderCache) {
				finderPath = finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<Guestbook> list = null;
		if (useFinderCache) {
			list = (List<Guestbook>)finderCache.getResult(finderPath, finderArgs);

			if (nonNull(list) && !list.isEmpty()) {
				for (Guestbook guestbook : list) {
					if (groupId != guestbook.getGroupId()) {
						list = null;
						break;
					}
				}
			}
		}

		if (isNull(list)) {
			StringBundler sb;
			if (nonNull(orderByComparator)) {
				sb = new StringBundler(3 + (orderByComparator.getOrderByFields().length * 2));
			} else {
				sb = new StringBundler(3);
			}

			sb.append(SQL_SELECT_GUESTBOOK_WHERE);
			sb.append(FINDER_COLUMN_GROUPID_GROUPID_2);
			if (nonNull(orderByComparator)) {
				appendOrderByComparator(sb, ORDER_BY_ENTITY_ALIAS, orderByComparator);
			} else {
				sb.append(GuestbookModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				queryPos.add(groupId);
				list = (List<Guestbook>) QueryUtil.list(query, getDialect(), start, end);
				cacheResult(list);
				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}
		return list;
	}

	/**
	 * Returns the first guestbook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching guestbook
	 * @throws NoSuchGuestbookException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook findByGroupId_First(long groupId, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByGroupId_First(groupId, orderByComparator);
		if (nonNull(guestbook)) {
			return guestbook;
		}
		StringBundler sb = new StringBundler(4);
		sb.append(NO_SUCH_ENTITY_WITH_KEY);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append("}");

		throw new NoSuchGuestbookException(sb.toString());
	}

	/**
	 * Returns the first guestbook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByGroupId_First(long groupId, OrderByComparator<Guestbook> orderByComparator) {
		List<Guestbook> list = findByGroupId(groupId, 0, 1, orderByComparator);
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * Returns the last guestbook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching guestbook
	 * @throws NoSuchGuestbookException if a matching guestbook could not be found
	 */
	@Override
	public Guestbook findByGroupId_Last(long groupId, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByGroupId_Last(groupId, orderByComparator);
		if (nonNull(guestbook)) {
			return guestbook;
		}
		StringBundler sb = new StringBundler(4);
		sb.append(NO_SUCH_ENTITY_WITH_KEY);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append("}");

		throw new NoSuchGuestbookException(sb.toString());
	}

	/**
	 * Returns the last guestbook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching guestbook, or <code>null</code> if a matching guestbook could not be found
	 */
	@Override
	public Guestbook fetchByGroupId_Last(long groupId, OrderByComparator<Guestbook> orderByComparator) {
		int count = countByGroupId(groupId);
		if (count == 0) {
			return null;
		}
		List<Guestbook> list = findByGroupId(groupId, count - 1, count, orderByComparator);
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * Returns the guestbooks before and after the current guestbook in the ordered set where groupId = &#63;.
	 *
	 * @param guestbookId the primary key of the current guestbook
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next guestbook
	 * @throws NoSuchGuestbookException if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook[] findByGroupId_PrevAndNext(long guestbookId, long groupId, OrderByComparator<Guestbook> orderByComparator)
			throws NoSuchGuestbookException {
		Guestbook guestbook = findByPrimaryKey(guestbookId);
		Session session = null;
		try {
			session = openSession();
			Guestbook[] array = new GuestbookImpl[3];
			array[0] = getByGroupId_PrevAndNext(session, guestbook, groupId, orderByComparator, true);
			array[1] = guestbook;
			array[2] = getByGroupId_PrevAndNext(session, guestbook, groupId, orderByComparator, false);

			return array;
		} catch (Exception exception) {
			throw processException(exception);
		} finally {
			closeSession(session);
		}
	}

	protected Guestbook getByGroupId_PrevAndNext(Session session, Guestbook guestbook, long groupId,
												 OrderByComparator<Guestbook> orderByComparator, boolean previous) {

		StringBundler sb;
		if (nonNull(orderByComparator)) {
			sb = new StringBundler(4 + (orderByComparator.getOrderByConditionFields().length * 3)
					+ (orderByComparator.getOrderByFields().length * 3));
		} else {
			sb = new StringBundler(3);
		}

		sb.append(SQL_SELECT_GUESTBOOK_WHERE);
		sb.append(FINDER_COLUMN_GROUPID_GROUPID_2);
		if (nonNull(orderByComparator)) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();
			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}
			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);
				if (orderByConditionFields.length > i + 1) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					} else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else if (orderByComparator.isAscending() ^ previous) {
					sb.append(WHERE_GREATER_THAN);
				} else {
					sb.append(WHERE_LESSER_THAN);
				}
			}
			sb.append(ORDER_BY_CLAUSE);
			String[] orderByFields = orderByComparator.getOrderByFields();
			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);
				if (orderByFields.length > i + 1) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else if (orderByComparator.isAscending() ^ previous) {
					sb.append(ORDER_BY_ASC);
				} else {
					sb.append(ORDER_BY_DESC);
				}
			}
		} else {
			sb.append(GuestbookModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();
		Query query = session.createQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(2);
		QueryPos queryPos = QueryPos.getInstance(query);
		queryPos.add(groupId);
		if (nonNull(orderByComparator)) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(guestbook)) {
				queryPos.add(orderByConditionValue);
			}
		}
		List<Guestbook> list = query.list();
		return list.size() == 2 ? list.get(1) : null;
	}

	/**
	 * Removes all the guestbooks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (Guestbook guestbook : findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(guestbook);
		}
	}

	/**
	 * Returns the number of guestbooks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching guestbooks
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = finderPathCountByGroupId;
		Object[] finderArgs = new Object[] {groupId};
		Long count = (Long) finderCache.getResult(finderPath, finderArgs);
		if (isNull(count)) {
			StringBundler sb = new StringBundler(2);
			sb.append(SQL_COUNT_GUESTBOOK_WHERE);
			sb.append(FINDER_COLUMN_GROUPID_GROUPID_2);
			String sql = sb.toString();
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				QueryPos queryPos = QueryPos.getInstance(query);
				queryPos.add(groupId);
				count = (Long) query.uniqueResult();
				finderCache.putResult(finderPath, finderArgs, count);
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Caches the guestbook in the entity cache if it is enabled.
	 *
	 * @param guestbook the guestbook
	 */
	@Override
	public void cacheResult(Guestbook guestbook) {
		entityCache.putResult(GuestbookImpl.class, guestbook.getPrimaryKey(), guestbook);
		finderCache.putResult(
				finderPathFetchByUUID_G,
				new Object[] {guestbook.getUuid(), guestbook.getGroupId()},
				guestbook);
	}

	/**
	 * Caches the guestbooks in the entity cache if it is enabled.
	 *
	 * @param guestbooks the guestbooks
	 */
	@Override
	public void cacheResult(List<Guestbook> guestbooks) {
		if ((valueObjectFinderCacheListThreshold == 0)
				|| ((valueObjectFinderCacheListThreshold > 0)
						&& (guestbooks.size() > valueObjectFinderCacheListThreshold))) {
			return;
		}
		for (Guestbook guestbook : guestbooks) {
			if (isNull(entityCache.getResult(GuestbookImpl.class, guestbook.getPrimaryKey()))) {
				cacheResult(guestbook);
			}
		}
	}

	/**
	 * Clears the cache for all guestbooks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(GuestbookImpl.class);
		finderCache.clearCache(GuestbookImpl.class);
	}

	/**
	 * Clears the cache for the guestbook.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Guestbook guestbook) {
		entityCache.removeResult(GuestbookImpl.class, guestbook);
	}

	@Override
	public void clearCache(List<Guestbook> guestbooks) {
		for (Guestbook guestbook : guestbooks) {
			entityCache.removeResult(GuestbookImpl.class, guestbook);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {finderCache.clearCache(GuestbookImpl.class);
		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(GuestbookImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(GuestbookModelImpl guestbookModelImpl) {
		Object[] args = new Object[] {guestbookModelImpl.getUuid(), guestbookModelImpl.getGroupId()};
		finderCache.putResult(finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(finderPathFetchByUUID_G, args, guestbookModelImpl);
	}

	/**
	 * Creates a new guestbook with the primary key. Does not add the guestbook to the database.
	 *
	 * @param guestbookId the primary key for the new guestbook
	 * @return the new guestbook
	 */
	@Override
	public Guestbook create(long guestbookId) {
		Guestbook guestbook = new GuestbookImpl();
		guestbook.setNew(true);
		guestbook.setPrimaryKey(guestbookId);
		String uuid = PortalUUIDUtil.generate();
		guestbook.setUuid(uuid);
		guestbook.setCompanyId(CompanyThreadLocal.getCompanyId());
		return guestbook;
	}

	/**
	 * Removes the guestbook with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param guestbookId the primary key of the guestbook
	 * @return the guestbook that was removed
	 * @throws NoSuchGuestbookException if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook remove(long guestbookId) throws NoSuchGuestbookException {
		return remove((Serializable)guestbookId);
	}

	/**
	 * Removes the guestbook with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the guestbook
	 * @return the guestbook that was removed
	 * @throws NoSuchGuestbookException if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook remove(Serializable primaryKey) throws NoSuchGuestbookException {
		Session session = null;
		try {
			session = openSession();
			Guestbook guestbook = (Guestbook)session.get(GuestbookImpl.class, primaryKey);
			if (isNull(guestbook)) {
				if (log.isDebugEnabled()) {
					log.debug(NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}
				throw new NoSuchGuestbookException(NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}
			return remove(guestbook);
		} catch (NoSuchGuestbookException noSuchEntityException) {
			throw noSuchEntityException;
		} catch (Exception exception) {
			throw processException(exception);
		} finally {
			closeSession(session);
		}
	}

	@Override
	protected Guestbook removeImpl(Guestbook guestbook) {
		Session session = null;
		try {
			session = openSession();
			if (!session.contains(guestbook)) {
				guestbook = (Guestbook)session.get(GuestbookImpl.class, guestbook.getPrimaryKeyObj());
			}
			if (nonNull(guestbook)) {
				session.delete(guestbook);
			}
		} catch (Exception exception) {
			throw processException(exception);
		} finally {
			closeSession(session);
		}
		if (nonNull(guestbook)) {
			clearCache(guestbook);
		}
		return guestbook;
	}

	@Override
	public Guestbook updateImpl(Guestbook guestbook) {
		boolean isNew = guestbook.isNew();
		if (!(guestbook instanceof GuestbookModelImpl)) {
			InvocationHandler invocationHandler;
			if (ProxyUtil.isProxyClass(guestbook.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(guestbook);
				throw new IllegalArgumentException("Implement ModelWrapper in guestbook proxy "
						+ invocationHandler.getClass());
			}
			throw new IllegalArgumentException("Implement ModelWrapper in custom Guestbook implementation "
					+ guestbook.getClass());
		}

		GuestbookModelImpl guestbookModelImpl = (GuestbookModelImpl)guestbook;
		if (Validator.isNull(guestbook.getUuid())) {
			String uuid = PortalUUIDUtil.generate();
			guestbook.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Date date = new Date();
		if (isNew && isNull(guestbook.getCreateDate())) {
			if (isNull(serviceContext)) {
				guestbook.setCreateDate(date);
			} else {
				guestbook.setCreateDate(serviceContext.getCreateDate(date));
			}
		}
		if (!guestbookModelImpl.hasSetModifiedDate()) {
			if (isNull(serviceContext)) {
				guestbook.setModifiedDate(date);
			} else {
				guestbook.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}
		Session session = null;
		try {
			session = openSession();
			if (isNew) {
				session.save(guestbook);
			}
			else {
				guestbook = (Guestbook)session.merge(guestbook);
			}
		} catch (Exception exception) {
			throw processException(exception);
		} finally {
			closeSession(session);
		}
		entityCache.putResult(GuestbookImpl.class, guestbookModelImpl, false, true);
		cacheUniqueFindersCache(guestbookModelImpl);
		if (isNew) {
			guestbook.setNew(false);
		}
		guestbook.resetOriginalValues();
		return guestbook;
	}

	/**
	 * Returns the guestbook with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the guestbook
	 * @return the guestbook
	 * @throws NoSuchGuestbookException if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook findByPrimaryKey(Serializable primaryKey) throws NoSuchGuestbookException {
		Guestbook guestbook = fetchByPrimaryKey(primaryKey);
		if (isNull(guestbook)) {
			if (log.isDebugEnabled()) {
				log.debug(NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}
			throw new NoSuchGuestbookException(NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}
		return guestbook;
	}

	/**
	 * Returns the guestbook with the primary key or throws a <code>NoSuchGuestbookException</code> if it could not be found.
	 *
	 * @param guestbookId the primary key of the guestbook
	 * @return the guestbook
	 * @throws NoSuchGuestbookException if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook findByPrimaryKey(long guestbookId) throws NoSuchGuestbookException {
		return findByPrimaryKey((Serializable)guestbookId);
	}

	/**
	 * Returns the guestbook with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param guestbookId the primary key of the guestbook
	 * @return the guestbook, or <code>null</code> if a guestbook with the primary key could not be found
	 */
	@Override
	public Guestbook fetchByPrimaryKey(long guestbookId) {
		return fetchByPrimaryKey((Serializable)guestbookId);
	}

	/**
	 * Returns all the guestbooks.
	 *
	 * @return the guestbooks
	 */
	@Override
	public List<Guestbook> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the guestbooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @return the range of guestbooks
	 */
	@Override
	public List<Guestbook> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the guestbooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of guestbooks
	 */
	@Override
	public List<Guestbook> findAll(int start, int end, OrderByComparator<Guestbook> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the guestbooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GuestbookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of guestbooks
	 * @param end the upper bound of the range of guestbooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of guestbooks
	 */
	@Override
	public List<Guestbook> findAll(int start, int end, OrderByComparator<Guestbook> orderByComparator, boolean useFinderCache) {
		FinderPath finderPath = null;
		Object[] finderArgs = null;
		if (start == QueryUtil.ALL_POS && end == QueryUtil.ALL_POS && isNull(orderByComparator)) {
			if (useFinderCache) {
				finderPath = finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		} else if (useFinderCache) {
			finderPath = finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}
		List<Guestbook> list = null;
		if (useFinderCache) {
			list = (List<Guestbook>) finderCache.getResult(finderPath, finderArgs);
		}
		if (isNull(list)) {
			StringBundler sb;
			String sql;
			if (nonNull(orderByComparator)) {
				sb = new StringBundler(2 + (orderByComparator.getOrderByFields().length * 2));
				sb.append(SQL_SELECT_GUESTBOOK);
				appendOrderByComparator(sb, ORDER_BY_ENTITY_ALIAS, orderByComparator);
				sql = sb.toString();
			} else {
				sql = SQL_SELECT_GUESTBOOK;
				sql = sql.concat(GuestbookModelImpl.ORDER_BY_JPQL);
			}
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(sql);
				list = (List<Guestbook>)QueryUtil.list(query, getDialect(), start, end);
				cacheResult(list);
				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}
		return list;
	}

	/**
	 * Removes all the guestbooks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Guestbook guestbook : findAll()) {
			remove(guestbook);
		}
	}

	/**
	 * Returns the number of guestbooks.
	 *
	 * @return the number of guestbooks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(finderPathCountAll, FINDER_ARGS_EMPTY);
		if (isNull(count)) {
			Session session = null;
			try {
				session = openSession();
				Query query = session.createQuery(SQL_COUNT_GUESTBOOK);
				count = (Long)query.uniqueResult();
				finderCache.putResult(finderPathCountAll, FINDER_ARGS_EMPTY, count);
			} catch (Exception exception) {
				throw processException(exception);
			} finally {
				closeSession(session);
			}
		}
		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "guestbookId";
	}

	@Override
	protected String getSelectSQL() {
		return SQL_SELECT_GUESTBOOK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return GuestbookModelImpl.TABLE_COLUMNS_MAP;
	}

	private void setGuestbookUtilPersistence(GuestbookPersistence guestbookPersistence) {
		try {
			Field field = GuestbookUtil.class.getDeclaredField("_persistence");
			field.setAccessible(true);
			field.set(null, guestbookPersistence);
		} catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(target = GBPersistenceConstants.SERVICE_CONFIGURATION_FILTER, unbind = "-")
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(target = GBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER, unbind = "-")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(target = GBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER, unbind = "-")
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}
}