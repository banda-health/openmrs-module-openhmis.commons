package org.openmrs.module.plm.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.plm.PersistentListException;
import org.openmrs.module.plm.PersistentListServiceImpl;
import org.openmrs.module.plm.PersistentListServiceProvider;
import org.openmrs.module.plm.model.PersistentListModel;

import java.util.ArrayList;

public class DatabaseServiceProvider implements PersistentListServiceProvider {
	private Log log = LogFactory.getLog(PersistentListServiceImpl.class);
	private SessionFactory sessionFactory;

	public DatabaseServiceProvider(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public PersistentListModel[] getLists() {
		Session session = sessionFactory.getCurrentSession();

		try {
			Criteria search = session.createCriteria(PersistentListModel.class);

			return new ArrayList<PersistentListModel>(search.list()).toArray(new PersistentListModel[0]);
		} catch (Exception ex) {
			throw new PersistentListException("An exception occurred while attempting to get the lists.", ex);
		} finally {
			session.close();
		}
	}

	@Override
	public void addList(PersistentListModel list) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.save(list);
		} catch (Exception ex) {
			throw new PersistentListException("An exception occurred while attempting to add a list.", ex);
		} finally {
			session.close();
		}
	}

	@Override
	public void removeList(String key) {
		Session session = sessionFactory.getCurrentSession();

		try {
			// Find the lists with the specified key (should only be one)
			Criteria search = session.createCriteria(PersistentListModel.class)
					.add(Restrictions.eq("key", key));

			// Delete the lists
			for (Object list : search.list()) {
				session.delete(list);
			}
			session.flush();
		} catch (Exception ex) {
			throw new PersistentListException("An exception occurred while attempting to add a list.", ex);
		} finally {
			session.close();
		}
	}
}
