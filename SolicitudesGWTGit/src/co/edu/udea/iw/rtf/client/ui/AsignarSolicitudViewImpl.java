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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link AsignarSolicitudView}.
 */
public class AsignarSolicitudViewImpl extends Composite implements
		AsignarSolicitudView {

	interface Binder extends UiBinder<Widget, AsignarSolicitudViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Label labelNombreCompleto;
	private Label labelCorreoElectronico;
	private Label labelTelefono;
	private Label labelCelular;
	private Label labelNombreSucursal;
	private Label labelNombreProducto;
	private Label labelNombreTipoSolicitud;
	private Label labelTextoSolicitud;
	private Label labelUsuarioAsigando;
	private TextBox textBoxNombreSolicitante;
	private TextBox textBoxCorreoElectronico;
	private TextBox textBoxTelefono;
	private TextBox textBoxCelular;
	private TextBox textBoxNombreSucursal;
	private TextBox textBoxNombreProducto;
	private TextBox textBoxNombreTipoSolicitud;
	private TextArea textBoxTextoSolicitud;
	private ListBox listBoxUsuarioAsignado;
	private Label labelUsuarioAsignadoError;
	@UiField
	Button buttonAsignarSolicitud;
	@UiField
	Button buttonCancelar;

	@UiHandler("buttonAsignarSolicitud")
	void onAsignarSolicitudClick(ClickEvent event) {
		listener.asignarSolicitud();
	}

	@UiHandler("buttonCancelar")
	void onCancelarClick(ClickEvent event) {
		listener.cancelarAsignacionSolicitud();
	}

	private Presenter listener;

	@UiField
	FlexTable tabla;

	public AsignarSolicitudViewImpl() {
		initWidget(binder.createAndBindUi(this));
		labelCelular = new Label("Celular: ");
		labelCorreoElectronico = new Label("Correo Electrónico: ");
		labelNombreCompleto = new Label("Nombre completo: ");
		labelNombreProducto = new Label("Producto: ");
		labelNombreSucursal = new Label("Sucursal: ");
		labelNombreTipoSolicitud = new Label("Tipo de solicitud: ");
		labelTelefono = new Label("Telefono: ");
		labelTextoSolicitud = new Label("Texto de la solicitud: ");
		labelUsuarioAsigando = new Label("usuario asignado: ");
		labelUsuarioAsignadoError = new Label("");
		textBoxCelular = new TextBox();
		textBoxCorreoElectronico = new TextBox();
		textBoxNombreProducto = new TextBox();
		textBoxNombreSolicitante = new TextBox();
		textBoxNombreSucursal = new TextBox();
		textBoxNombreTipoSolicitud = new TextBox();
		textBoxTelefono = new TextBox();
		textBoxTextoSolicitud = new TextArea();
		listBoxUsuarioAsignado = new ListBox();
		textBoxCelular.setReadOnly(true);
		textBoxCorreoElectronico.setReadOnly(true);
		textBoxNombreProducto.setReadOnly(true);
		textBoxNombreSolicitante.setReadOnly(true);
		textBoxNombreSucursal.setReadOnly(true);
		textBoxNombreTipoSolicitud.setReadOnly(true);
		textBoxTelefono.setReadOnly(true);
		textBoxTextoSolicitud.setReadOnly(true);
		tabla.setWidget(0, 0, labelNombreCompleto);
		tabla.setWidget(0, 1, textBoxNombreSolicitante);
		tabla.setWidget(1, 0, labelCorreoElectronico);
		tabla.setWidget(1, 1, textBoxCorreoElectronico);
		tabla.setWidget(2, 0, labelTelefono);
		tabla.setWidget(2, 1, textBoxTelefono);
		tabla.setWidget(3, 0, labelCelular);
		tabla.setWidget(3, 1, textBoxCelular);
		tabla.setWidget(4, 0, labelNombreSucursal);
		tabla.setWidget(4, 1, textBoxNombreSucursal);
		tabla.setWidget(5, 0, labelNombreProducto);
		tabla.setWidget(5, 1, textBoxNombreProducto);
		tabla.setWidget(6, 0, labelNombreTipoSolicitud);
		tabla.setWidget(6, 1, textBoxNombreTipoSolicitud);
		tabla.setWidget(7, 0, labelTextoSolicitud);
		tabla.setWidget(7, 1, textBoxTextoSolicitud);
		tabla.setWidget(8, 0, labelUsuarioAsigando);
		tabla.setWidget(8, 1, listBoxUsuarioAsignado);
		tabla.setWidget(8, 2, labelUsuarioAsignadoError);
		textBoxCelular.addStyleName("textBox");
		textBoxCorreoElectronico.addStyleName("textBox");
		textBoxNombreProducto.addStyleName("textBox");
		textBoxNombreSolicitante.addStyleName("textBox");
		textBoxNombreSucursal.addStyleName("textBox");
		textBoxNombreTipoSolicitud.addStyleName("textBox");
		textBoxTelefono.addStyleName("textBox");
		textBoxTextoSolicitud.addStyleName("area");
		listBoxUsuarioAsignado.addStyleName("lista");
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public TextBox getTextBoxNombreCompleto() {
		// TODO Auto-generated method stub
		return textBoxNombreSolicitante;
	}

	@Override
	public TextBox getTextBoxCorreoElectronico() {
		// TODO Auto-generated method stub
		return textBoxCorreoElectronico;
	}

	@Override
	public TextBox getTextBoxTelefono() {
		// TODO Auto-generated method stub
		return textBoxTelefono;
	}

	@Override
	public TextBox getTextBoxCelular() {
		// TODO Auto-generated method stubnull
		return textBoxCelular;
	}

	@Override
	public TextBox getTextBoxNombreSucursal() {
		// TODO Auto-generated method stub
		return textBoxNombreSucursal;
	}

	@Override
	public TextBox getTextBoxNombreProducto() {
		// TODO Auto-generated method stub
		return textBoxNombreProducto;
	}

	@Override
	public TextBox getTextBoxNombreTipoSolicitud() {
		// TODO Auto-generated method stub
		return textBoxNombreTipoSolicitud;
	}

	@Override
	public TextArea getTextBoxTextoSolicitud() {
		// TODO Auto-generated method stub
		return textBoxTextoSolicitud;
	}

	@Override
	public ListBox getListBoxUsuario() {
		// TODO Auto-generated method stub
		return listBoxUsuarioAsignado;
	}

	@Override
	public Label getLabelusuarioError() {
		// TODO Auto-generated method stub
		return labelUsuarioAsignadoError;
	}
}
