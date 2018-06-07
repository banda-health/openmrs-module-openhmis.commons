/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.entity.db.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides access to a data source through hibernate.
 */
public class HibernateRepository implements IHibernateRepository {
	private DbSessionFactory sessionFactory;

	public HibernateRepository(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DbSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Query createQuery(String query) {
		DbSession session = sessionFactory.getCurrentSession();
		return session.createQuery(query);
	}

	@Override
	public <E extends OpenmrsObject> Criteria createCriteria(Class<E> cls) {
		DbSession session = sessionFactory.getCurrentSession();
		return session.createCriteria(cls);
	}

	@Override
	public <E extends OpenmrsObject> E save(E entity) {
		DbSession session = sessionFactory.getCurrentSession();

		try {
			session.saveOrUpdate(entity);
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to add a " + entity.getClass().getSimpleName()
			        + " entity.", ex);
		}

		return entity;
	}

	@Override
	@Transactional
	public void saveAll(Collection<? extends OpenmrsObject> collection) {
		DbSession session = sessionFactory.getCurrentSession();
		try {

			if (collection != null && !collection.isEmpty()) {
				for (OpenmrsObject obj : collection) {
					session.saveOrUpdate(obj);
				}
			}
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to add a entity.", ex);
		}
	}

	@Override
	public <E extends OpenmrsObject> void delete(E entity) {
		DbSession session = sessionFactory.getCurrentSession();
		try {
			session.delete(entity);
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to delete a "
			        + entity.getClass().getSimpleName() + " entity.", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T selectValue(Criteria criteria) {
		try {
			return (T)criteria.uniqueResult();
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to selecting a value.", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T selectValue(Query query) {
		try {
			return (T)query.uniqueResult();
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to selecting a value.", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> E selectSingle(Class<E> cls, Serializable id) {
		DbSession session = sessionFactory.getCurrentSession();

		try {
			return (E)session.get(cls, id);
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to select a single " + cls.getSimpleName()
			        + " entity with ID" + " " + id.toString() + ".", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> E selectSingle(Class<E> cls, Criteria criteria) {
		E result = null;
		try {
			List<E> results = criteria.list();

			if (!results.isEmpty()) {
				result = results.get(0);
			}
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to select a single " + cls.getSimpleName()
			        + " entity.", ex);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> List<E> select(Class<E> cls) {
		DbSession session = sessionFactory.getCurrentSession();

		try {
			Criteria search = session.createCriteria(cls);

			return search.list();
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to get " + cls.getSimpleName() + " entities.", //
			        ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> List<E> select(Class<E> cls, Criteria criteria) {
		// If the criteria is not defined just use the default select method
		if (criteria == null) {
			return select(cls);
		}

		List<E> results;

		try {
			results = criteria.list();
		} catch (Exception ex) {
			throw new APIException(
			        "An exception occurred while attempting to select " + cls.getSimpleName() + " entities.", ex);
		}

		return results;
	}
}
