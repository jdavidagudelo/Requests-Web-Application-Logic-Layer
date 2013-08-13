package co.edu.udea.iw.rtf.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.udea.iw.rtf.dao.UsuarioDAO;
import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Implementación para acceder a la tabla de usuarios usando el framework
 * Hibernate.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class UsuarioDAOHibernate extends HibernateDaoSupport implements
		UsuarioDAO {

	public List<Usuario> obtener() throws IWDaoException {

		List<Usuario> usuarios = new ArrayList<Usuario>();
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Usuario> list = sesion.createCriteria(Usuario.class).list();
			usuarios = list;
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return usuarios;
	}

	public Usuario obtener(String login) throws IWDaoException {
		Session sesion = null;
		Usuario usuario = null;
		try {

			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			usuario = (Usuario) sesion.get(Usuario.class, login);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return usuario;
	}

	public Usuario guardar(Usuario usuario) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.save(usuario);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return usuario;
	}

	public Usuario actualizar(Usuario usuario) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.update(usuario);
			sesion.flush();
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return usuario;
	}

	public void eliminar(Usuario usuario) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.delete(usuario);
			sesion.flush();
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

	}
}