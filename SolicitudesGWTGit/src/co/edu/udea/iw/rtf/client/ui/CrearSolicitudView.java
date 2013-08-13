/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package co.edu.udea.iw.rtf.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * View base interface. Extends IsWidget so a view impl can easily provide its
 * container widget.
 * @author Juan David Agudelo jdaaa2009@gmail.com
 */
public interface CrearSolicitudView extends IsWidget {

	/**
	 * TextBox que contiene los nombres del solicitante de una solicitud.
	 * 
	 * @return TextBox que contiene los nombres del solicitante de una
	 *         solicitud.
	 * */
	public TextBox getTextBoxNombresSolicitante();

	/**
	 * TextBox que contiene los apellidos del solicitante de una solicitud.
	 * 
	 * @return TextBox que contiene los apellidos del solicitante de una
	 *         solicitud.
	 * */
	public TextBox getTextBoxApellidosSolicitante();

	/**
	 * TextBox que contiene el correo electronico del solicitante.
	 * 
	 * @return TextBox que contiene el correo electronico del solicitante.
	 * */
	public TextBox getTextBoxCorreoElectronico();

	/**
	 * TextBox que contiene la confirmación del correo electronico.
	 * 
	 * @return TextBox que contiene la confirmación del correo electronico.
	 * */
	public TextBox getTextBoxConfirmacionCorreo();

	/**
	 * TextBox que contiene el telefono del solicitante.
	 * 
	 * @return Textbox que contiene el telefono del solicitante.
	 * */
	public TextBox getTextBoxTelefono();

	/**
	 * TextBox que contiene el celular del solicitante.
	 * 
	 * @return TextBox que contiene el celular del solicitante.
	 * */
	public TextBox getTextBoxCelular();

	/**
	 * TextBox que contiene el otro producto ingresado por el solicitante.
	 * 
	 * @return Textbox que contiene el otro producto ingresado por el
	 *         solicitante.
	 * */
	public TextBox getTextBoxOtroProducto();

	/**
	 * ListBox que contiene la lista de productos.
	 * 
	 * @return ListBox que contiene la lista de productos.
	 * */
	public ListBox getListBoxProducto();

	/**
	 * ListBox que contiene la lista de sucursales.
	 * 
	 * @return ListBox que contiene la lista de sucursales.
	 * */
	public ListBox getListBoxSucursal();

	/**
	 * ListBox que contiene la tipos de solicitud.
	 * 
	 * @return ListBox que contiene la lista de productos.
	 * */
	public ListBox getListBoxTipoSolicitud();

	/**
	 * TextBox que contiene el texto de la solicitud.
	 * 
	 * @return TextBox que contiene el texto de la solicitud.
	 * */
	public TextArea getTextBoxTextoSolicitud();

	/**
	 * DialogBox usado para mostrar un mensaje al usuario cada vez que se crea
	 * una nueva solicitud o se presenta algún error.
	 * 
	 * @return DialogBox usado para mostrar un mensaje al usuario al crear una
	 *         solicitud o presentarse un error.
	 * */
	public DialogBox getDialogBox();

	/**
	 * Button que contiene el dialogo.
	 * 
	 * @return Button usado para cerrar el DialogBox para mostrar mensajes.
	 * */
	public Button getCloseDialogButton();

	/**
	 * Label usada para mostrar los errores en el nombre del solicitante.
	 * 
	 * @return Label usada para mostrar los errores en los nombres del
	 *         solicitante.
	 * */
	public Label getLabelNombresSolicitanteError();

	/**
	 * Label usada para mostrar los errores en el apellido del solicitante.
	 * 
	 * @return Label usada para mostrar los errores en los apelldos del
	 *         solicitante.
	 * */
	public Label getLabelApellidosSolicitanteError();

	/**
	 * Label usada para mostrar los errores en el correo electronico del
	 * solicitante.
	 * 
	 * @return Label usada para mostrar los errores en el correo electronico del
	 *         solicitante.
	 * */
	public Label getLabelCorreoElectronicoError();

	/**
	 * Label usada para mostrar los errores en la confirmacion del correo
	 * electronico del solicitante.
	 * 
	 * @return Label usada para mostrar los errores en la confirmacion del
	 *         correo electronico del solicitante.
	 * */
	public Label getLabelConfirmacionCorreoError();

	/**
	 * Label usada para mostrar los errores en el telefono del solicitante.
	 * 
	 * @return Label usada para mostrar los errores en el telefono del
	 *         solicitante.
	 * */
	public Label getLabelTelefonoError();

	/**
	 * Label usada para mostrar los errores en el celular del solicitante.
	 * 
	 * @return Label usada para mostrar los errores en el celular del
	 *         solicitante.
	 * */
	public Label getLabelCelularError();

	/**
	 * Label usada para mostrar los errores en el texto de la solicitud.
	 * 
	 * @return Label usada para mostrar los errores en el texto de la solicitud.
	 * */
	public Label getLabelTextSolicitudError();

	/**
	 * Label usada para mostrar los errores en el otro producto ingresado por el
	 * solicitante.
	 * 
	 * @return Label usada para mostrar los errores en el otro producto.
	 * */
	public Label getLabelOtroProductoError();

	/**
	 * Label usada para mostrar los errores en el producto.
	 * 
	 * @return Label usada para mostrar los errores en el producto.
	 * */
	public Label getLabelProductoError();

	/**
	 * Label usada para mostrar los errores en la sucursal.
	 * 
	 * @return Label usada para mostrar los errores en la sucursal.
	 * */
	public Label getLabelSucursalError();

	/**
	 * Label usada para mostrar los errores en el tipo de solicitud.
	 * 
	 * @return Label usada para mostrar los errores en el tipo de solicitud.
	 * */
	public Label getLabelTipoSolicitudError();

	void setPresenter(Presenter listener);

	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);

		/**
		 * Método que permite guardar una solicitud.
		 * */
		public void guardarSolicitud();

		/**
		 * Metodo que permite limpiar el formulario
		 */
		public void limpiarFormulario();
	}
}
