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
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link CrearSolicitudView}.
 * @author Juan David Agudelo Alvarez
 */
public class CrearSolicitudViewImpl extends Composite implements
		CrearSolicitudView {

	interface Binder extends UiBinder<Widget, CrearSolicitudViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	private TextBox textBoxNombresSolicitante;
	private TextBox textBoxApellidosSolicitante;
	private TextBox textBoxCorreoElectronico;
	private TextBox textBoxConfirmacionCorreo;
	private TextBox textBoxTelefono;
	private TextBox textBoxCelular;
	private TextBox textBoxOtroProducto;
	private ListBox listBoxSuscursal;
	private ListBox listBoxProducto;
	private TextArea textBoxTextoSolicitud;
	private ListBox listBoxTipoSolicitud;
	private Label labelNombresSolicitante;
	private Label labelApellidosSolicitante;
	private Label labelCorreoElectronico;
	private Label labelConfirmacionCorreo;
	private Label labelTelefono;
	private Label labelCelular;
	private Label labelOtroProducto;
	private Label labelSucursal;
	private Label labelProducto;
	private Label labelTextoSolicitud;
	private Label labelTipoSolicitud;
	private Label labelNombresSolicitanteError;
	private Label labelApellidosSolicitanteError;
	private Label labelCorreoElectronicoError;
	private Label labelConfirmacionCorreoError;
	private Label labelTelefonoError;
	private Label labelCelularError;
	private Label labelOtroProductoError;
	private Label labelSucursalError;
	private Label labelProductoError;
	private Label labelTextoSolicitudError;
	private Label labelTipoSolicitudError;
	private DialogBox dialogBox;
	private Presenter listener;
	
	@UiField
	Button buttonCrearSolicitud;

	@UiField
	Button buttonLimpiarForm;

	@UiField
	FlexTable tabla;
	private Button closeButton;

	public CrearSolicitudViewImpl() {
		initWidget(binder.createAndBindUi(this));
		
		labelNombresSolicitante = new Label("* Nombres: ");
		labelApellidosSolicitante = new Label("* Apellidos: ");
		labelCelular = new Label("Celular: ");
		labelCorreoElectronico = new Label("* Correo electrónico: ");
		labelConfirmacionCorreo = new Label("* Confirmación del correo: ");
		labelTelefono = new Label("* Telefono: ");
		labelOtroProducto = new Label("Otro producto: ");
		labelProducto = new Label("* Producto: ");
		labelSucursal = new Label("* Sucursal: ");
		labelTextoSolicitud = new Label("* Texto de la solicitud: ");
		labelTipoSolicitud = new Label("* Tipo de solicitud: ");
		
		labelNombresSolicitanteError = new Label();
		labelApellidosSolicitanteError = new Label();
		labelCelularError = new Label();
		labelCorreoElectronicoError = new Label();
		labelConfirmacionCorreoError = new Label();
		labelTelefonoError = new Label();
		labelOtroProductoError = new Label();
		labelProductoError = new Label();
		labelSucursalError = new Label();
		labelTextoSolicitudError = new Label();
		labelTipoSolicitudError = new Label();
		textBoxApellidosSolicitante = new TextBox();
		textBoxCelular = new TextBox();
		textBoxConfirmacionCorreo = new TextBox();
		textBoxCorreoElectronico = new TextBox();
		textBoxNombresSolicitante = new TextBox();
		textBoxOtroProducto = new TextBox();
		textBoxTelefono = new TextBox();
		textBoxTextoSolicitud = new TextArea();
		listBoxProducto = new ListBox();
		listBoxSuscursal = new ListBox();
		listBoxTipoSolicitud = new ListBox();
		
		textBoxNombresSolicitante.setMaxLength(45);
		textBoxApellidosSolicitante.setMaxLength(45);
		textBoxTelefono.setMaxLength(15);
		textBoxCelular.setMaxLength(15);
		textBoxCorreoElectronico.setMaxLength(120);
		textBoxConfirmacionCorreo.setMaxLength(120);
		
		labelNombresSolicitante.addStyleName("label");
		labelApellidosSolicitante.addStyleName("label");
		labelCelular.addStyleName("label");
		labelCorreoElectronico.addStyleName("label");
		labelConfirmacionCorreo.addStyleName("label");
		labelTelefono.addStyleName("label");
		labelOtroProducto.addStyleName("label");
		labelProducto.addStyleName("label");
		labelSucursal.addStyleName("label");
		labelTextoSolicitud.addStyleName("label");
		labelTipoSolicitud.addStyleName("label");
		textBoxNombresSolicitante.addStyleName("textBox");
		textBoxApellidosSolicitante.addStyleName("textBox");
		textBoxCorreoElectronico.addStyleName("textBox");
		textBoxConfirmacionCorreo.addStyleName("textBox");
		textBoxTelefono.addStyleName("textBox");
		textBoxCelular.addStyleName("textBox");
		textBoxOtroProducto.addStyleName("textBox");
		textBoxTextoSolicitud.addStyleName("area");
		listBoxProducto.addStyleName("lista");
		listBoxSuscursal.addStyleName("lista");
		listBoxTipoSolicitud.addStyleName("lista");
		
		
		tabla.setWidget(0, 0, labelNombresSolicitante);
		tabla.setWidget(0, 1, textBoxNombresSolicitante);
		tabla.setWidget(0, 2, labelNombresSolicitanteError);
		tabla.setWidget(1, 0, labelApellidosSolicitante);
		tabla.setWidget(1, 1, textBoxApellidosSolicitante);
		tabla.setWidget(1, 2, labelApellidosSolicitanteError);
		tabla.setWidget(2, 0, labelCorreoElectronico);
		tabla.setWidget(2, 1, textBoxCorreoElectronico);
		tabla.setWidget(2, 2, labelCorreoElectronicoError);
		tabla.setWidget(3, 0, labelConfirmacionCorreo);
		tabla.setWidget(3, 1, textBoxConfirmacionCorreo);
		tabla.setWidget(3, 2, labelConfirmacionCorreoError);
		tabla.setWidget(4, 0, labelTelefono);
		tabla.setWidget(4, 1, textBoxTelefono);
		tabla.setWidget(4, 2, labelTelefonoError);
		tabla.setWidget(5, 0, labelCelular);
		tabla.setWidget(5, 1, textBoxCelular);
		tabla.setWidget(5, 2, labelCelularError);
		tabla.setWidget(6, 0, labelProducto);
		tabla.setWidget(6, 1, listBoxProducto);
		tabla.setWidget(6, 2, labelProductoError);
		tabla.setWidget(7, 0, labelOtroProducto);
		tabla.setWidget(7, 1, textBoxOtroProducto);
		tabla.setWidget(7, 2, labelOtroProductoError);
		tabla.setWidget(8, 0, labelSucursal);
		tabla.setWidget(8, 1, listBoxSuscursal);
		tabla.setWidget(8, 2, labelSucursalError);
		tabla.setWidget(9, 0, labelTipoSolicitud);
		tabla.setWidget(9, 1, listBoxTipoSolicitud);
		tabla.setWidget(9, 2, labelTipoSolicitudError);
		tabla.setWidget(10, 0, labelTextoSolicitud);
		tabla.setWidget(10, 1, textBoxTextoSolicitud);
		tabla.setWidget(10, 2, labelTextoSolicitudError);
		
		dialogBox = new DialogBox();
		dialogBox.setText("Default Dialog Text");
		dialogBox.setAnimationEnabled(true);
		closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		dialogBox.hide();
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@UiHandler("buttonCrearSolicitud")
	void onCrearSolicitudClick(ClickEvent event) {
		listener.guardarSolicitud();
	}

	@UiHandler("buttonLimpiarForm")
	void onLimpiarForm(ClickEvent event) {
		listener.limpiarFormulario();
	}

	@Override
	public TextBox getTextBoxNombresSolicitante() {
		// TODO Auto-generated method stub
		return textBoxNombresSolicitante;
	}

	@Override
	public TextBox getTextBoxApellidosSolicitante() {
		// TODO Auto-generated method stub
		return textBoxApellidosSolicitante;
	}

	@Override
	public TextBox getTextBoxCorreoElectronico() {
		// TODO Auto-generated method stub
		return textBoxCorreoElectronico;
	}

	@Override
	public TextBox getTextBoxConfirmacionCorreo() {
		// TODO Auto-generated method stub
		return textBoxConfirmacionCorreo;
	}

	@Override
	public TextBox getTextBoxTelefono() {
		// TODO Auto-generated method stub
		return textBoxTelefono;
	}

	@Override
	public TextBox getTextBoxCelular() {
		// TODO Auto-generated method stub
		return textBoxCelular;
	}

	@Override
	public TextBox getTextBoxOtroProducto() {
		// TODO Auto-generated method stub
		return textBoxOtroProducto;
	}

	@Override
	public ListBox getListBoxProducto() {
		// TODO Auto-generated method stub
		return listBoxProducto;
	}

	@Override
	public ListBox getListBoxSucursal() {
		// TODO Auto-generated method stub
		return listBoxSuscursal;
	}

	@Override
	public ListBox getListBoxTipoSolicitud() {

		// TODO Auto-generated method stub
		return listBoxTipoSolicitud;
	}

	@Override
	public TextArea getTextBoxTextoSolicitud() {
		// TODO Auto-generated method stub
		return textBoxTextoSolicitud;
	}

	@Override
	public DialogBox getDialogBox() {
		// TODO Auto-generated method stub
		return dialogBox;
	}

	@Override
	public Button getCloseDialogButton() {
		return closeButton;
	}

	@Override
	public Label getLabelNombresSolicitanteError() {
		// TODO Auto-generated method stub
		return labelNombresSolicitanteError;
	}

	@Override
	public Label getLabelApellidosSolicitanteError() {
		// TODO Auto-generated method stub
		return labelApellidosSolicitanteError;
	}

	@Override
	public Label getLabelCorreoElectronicoError() {
		// TODO Auto-generated method stub
		return labelCorreoElectronicoError;
	}

	@Override
	public Label getLabelConfirmacionCorreoError() {
		// TODO Auto-generated method stub
		return labelConfirmacionCorreoError;
	}

	@Override
	public Label getLabelTelefonoError() {
		// TODO Auto-generated method stub
		return labelTelefonoError;
	}

	@Override
	public Label getLabelCelularError() {
		// TODO Auto-generated method stub
		return labelCelularError;
	}

	@Override
	public Label getLabelTextSolicitudError() {
		// TODO Auto-generated method stub
		return labelTextoSolicitudError;
	}

	@Override
	public Label getLabelOtroProductoError() {
		// TODO Auto-generated method stub
		return labelOtroProductoError;
	}

	@Override
	public Label getLabelProductoError() {
		// TODO Auto-generated method stub
		return labelProductoError;
	}

	@Override
	public Label getLabelSucursalError() {
		// TODO Auto-generated method stub
		return labelSucursalError;
	}

	@Override
	public Label getLabelTipoSolicitudError() {
		// TODO Auto-generated method stub
		return labelTipoSolicitudError;
	}
}
