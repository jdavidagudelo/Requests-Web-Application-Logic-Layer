package co.edu.udea.iw.rtf.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.udea.iw.rtf.dao.SucursalDAO;
import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Implementación para acceder a la tabla de sucursales usando el framework
 * Hibernate.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class SucursalDAOHibernate extends HibernateDaoSupport implements
		SucursalDAO {
	public List<Sucursal> obtener() throws IWDaoException {

		List<Sucursal> sucursales = new ArrayList<Sucursal>();
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();

			Transaction t = sesion.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Sucursal> list = sesion.createCriteria(Sucursal.class).list();
			sucursales = list;
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return sucursales;
	}

	public Sucursal obtener(Long codigo) throws IWDaoException {
		Session sesion = null;
		Sucursal sucursal = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sucursal = (Sucursal) sesion.get(Sucursal.class, codigo);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return sucursal;
	}

	public Sucursal guardar(Sucursal sucursal) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.save(sucursal);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return sucursal;
	}

	public Sucursal actualizar(Sucursal sucursal) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.update(sucursal);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return sucursal;
	}

	public void eliminar(Sucursal sucursal) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.delete(sucursal);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

	}

	public Long obtenerMaximoCodigo() throws IWDaoException {
		Session sesion = null;
		Long codigo = 0l;
		try {
			sesion = getSessionFactory().openSession();

			Transaction t = sesion.beginTransaction();
			codigo = (Long) sesion.createQuery(
					"SELECT MAX(codigo) FROM Sucursal").uniqueResult();
			t.commit();
			if (codigo == null)
				codigo = 0l;

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return codigo;
	}
}