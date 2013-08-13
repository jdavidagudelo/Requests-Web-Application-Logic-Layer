package co.edu.udea.iw.rtf.util.mail;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Clase que envia un correo electrónico
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * 
 */
public class EnviarEmail {

	/**
	 * Permite enviar un correo a un destino con un cuerpo y un asunto.
	 * 
	 * @param receptor
	 *            direccion de correo a la que se envia el mensaje.
	 * @param subject
	 *            asunto del correo
	 * @param body
	 *            cuerpo del correo
	 */
	public void sendEmail(String receptor, String subject, String body)
			throws IWDaoException {
		try {
			ResourceBundle configuracion = ResourceBundle
					.getBundle("co.edu.udea.iw.rtf.util.properties.configuracion");
			Properties properties = System.getProperties();

			properties.setProperty("mail.smtp.host",
					configuracion.getString("mail.smtp.host"));

			// TLS si esta disponible
			properties.setProperty("mail.smtp.starttls.enable",
					configuracion.getString("mail.smtp.starttls.enable"));
			// props.setProperty("mail.smtp.starttls.enable",
			// FacesContext.getCurrentInstance().getExternalContext().getInitParameter("mail.smtp.starttls.enable"));

			// Puerto de gmail para envio de correos
			properties.setProperty("mail.smtp.port",
					configuracion.getString("mail.smtp.port"));
			// props.setProperty("mail.smtp.port",
			// FacesContext.getCurrentInstance().getExternalContext().getInitParameter("mail.smtp.port"));

			// Si requiere o no usuario y password para
			// conectarse.mail.smtp.auth
			properties.setProperty("mail.smtp.auth",
					configuracion.getString("mail.smtp.auth"));

			Session session = Session.getDefaultInstance(properties);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(configuracion
					.getString("mail.smtp.user")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					receptor));

			message.setSubject(subject);
			message.setText(body);
			// message.

			Transport t = session.getTransport("smtp");
			t.connect(configuracion.getString("mail.smtp.user"),
					configuracion.getString("mail.smtp.pws"));
			t.sendMessage(message, message.getAllRecipients());
			t.close();

		} catch (AddressException e) {
			throw new IWDaoException(
					"Direccion de destino del correo invalida.");
		} catch (MessagingException e) {
			throw new IWDaoException("No se pudo enviar el mensaje"
					+ e.toString());
		}
	}

}
