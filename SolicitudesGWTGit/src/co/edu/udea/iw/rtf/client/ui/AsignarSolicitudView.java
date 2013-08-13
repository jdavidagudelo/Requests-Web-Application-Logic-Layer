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
public interface AsignarSolicitudView extends IsWidget {

	void setPresenter(Presenter listener);

	/**
	 * TextBox para el nombre completo del solicitante.
	 * 
	 * @return Textbox para nombre completo del solicitante.
	 * */
	public TextBox getTextBoxNombreCompleto();

	/**
	 * TextBox para el correo del solicitante.
	 * 
	 * @return TextBox para el correo del solicitante.
	 * */
	public TextBox getTextBoxCorreoElectronico();

	/**
	 * TextBox para el telefono del solicitante.
	 * 
	 * @return TextBox para el telefono del solicitante.
	 * */
	public TextBox getTextBoxTelefono();

	/**
	 * TextBox para el correo del solicitante.
	 * 
	 * @return TextBox para el correo del solicitante.
	 * */
	public TextBox getTextBoxCelular();

	/**
	 * TextBox para el nombre de la sucursal a la que se solicita.
	 * 
	 * @return TextBox para el nombre de la sucursal.
	 * */
	public TextBox getTextBoxNombreSucursal();

	/**
	 * TextBox para el nombre del producto.
	 * 
	 * @return TextBox para el nombre del producto.
	 * */
	public TextBox getTextBoxNombreProducto();

	/**
	 * TextBox para el nombre del tipo de solicitud.
	 * 
	 * @return TextBox para el nombre del tipo de solicitud.
	 * */
	public TextBox getTextBoxNombreTipoSolicitud();

	/**
	 * TextBox para el nombre del producto.
	 * 
	 * @return TextBox para el nombre del producto.
	 * */
	public TextArea getTextBoxTextoSolicitud();

	/**
	 * ListBox para el usuario a seleccionar.
	 * 
	 * @return ListBox para el usuario a seleccionar.
	 * */
	public ListBox getListBoxUsuario();

	/**
	 * Label para errores en el ingreso de usuario.
	 * 
	 * @return Label para errores en el ingreso del usuario.
	 * */
	public Label getLabelusuarioError();

	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);

		/**
		 * Metodo usado para asignar una solicitud a un usuario.
		 * */
		public void asignarSolicitud();

		/**
		 * Metodo usado para cancelar la asignacion de 
		 * una solicitud.
		 * */
		public void cancelarAsignacionSolicitud();
	}
}
