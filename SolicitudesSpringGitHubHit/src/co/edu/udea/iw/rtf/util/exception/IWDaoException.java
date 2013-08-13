package co.edu.udea.iw.rtf.util.exception;

import org.apache.log4j.Logger;

/**
 * Esta clase es utilizada para manejar excepciones causadas por el acceso a los
 * datos.
 * 
 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class IWDaoException extends Exception {
	Logger log = Logger.getLogger(IWDaoException.class);

	public IWDaoException() {
		super();
		log.error(this.getMessage(), this);
	}

	public IWDaoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		log.error(arg0, arg1);
	}

	public IWDaoException(String arg0) {
		super(arg0);
		log.error(arg0, this);
	}

	public IWDaoException(Throwable arg0) {
		super(arg0);
		log.error(arg0.getMessage(), arg0);
	}

	private static final long serialVersionUID = 1L;

}
