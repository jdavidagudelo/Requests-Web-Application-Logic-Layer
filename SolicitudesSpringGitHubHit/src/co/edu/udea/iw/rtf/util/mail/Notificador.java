package co.edu.udea.iw.rtf.util.mail;

import org.apache.log4j.Logger;

import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Esta clase permite realizar el proceso de enviar el correo a un cliente como
 * un hilo que se ejecuta en segundo plano.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * */
public class Notificador extends Thread {

	private String correo = null;
	private String subject = null;
	private String texto = null;

	private static Logger log = Logger.getLogger(Notificador.class);

	public Notificador(String correo, String subject, String texto) {

		this.correo = correo;
		this.subject = subject;
		this.texto = texto;
	}

	@Override
	public void run() {
		EnviarEmail enviarEmail = new EnviarEmail();
		try {
			enviarEmail.sendEmail(correo, subject, texto);
		} catch (IWDaoException e) {
			log.error(e);
		}
	}

}
