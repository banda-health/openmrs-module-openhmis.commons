/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;

import java.io.Serializable;
import java.util.List;

public class GenericHibernateDAO implements IGenericHibernateDAO {
	SessionFactory sessionFactory;

	public GenericHibernateDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public <E extends OpenmrsObject> Criteria createCriteria(Class<E> cls) {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(cls);
	}

	@Override
	public <E extends OpenmrsObject> E save(E entity) throws APIException {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.saveOrUpdate(entity);
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to add a " + entity.getClass().getSimpleName() + " entity.", ex);
		}
		return entity;
	}

	@Override
	public <E extends OpenmrsObject> void delete(E entity) throws APIException {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(entity);
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to delete a " + entity.getClass().getSimpleName() + " entity.", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T selectValue(Criteria criteria) {
		try {
			return (T)criteria.uniqueResult();
		}
		catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to selecting a value.", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> E selectSingle(Class<E> cls, Serializable id) throws APIException {
		Session session = sessionFactory.getCurrentSession();

		try {
			return (E)session.get(cls, id);
		}
		catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to select a single " + cls.getSimpleName() + " entity with ID " + id.toString() + ".", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> E selectSingle(Class<E> cls, Criteria criteria) throws APIException {
		E result = null;
		try {
			List<E> results = criteria.list();

			if (results.size() > 0) {
				result = results.get(0);
			}
		}
		catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to select a single " + cls.getSimpleName() + " entity.", ex);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> List<E> select(Class<E> cls) throws APIException {
		Session session = sessionFactory.getCurrentSession();

		try {
			Criteria search = session.createCriteria(cls);

			return search.list();
		} catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to get " + cls.getSimpleName() + " entities.", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E extends OpenmrsObject> List<E> select(Class<E> cls, Criteria criteria) throws APIException {
		// If the criteria is not defined just use the default select method
		if (criteria == null) {
			return select(cls);
		}

		List<E> results;

		try {
			results = criteria.list();
		}
		catch (Exception ex) {
			throw new APIException("An exception occurred while attempting to select " + cls.getSimpleName() + " entities.", ex);
		}

		return results;
	}
}

