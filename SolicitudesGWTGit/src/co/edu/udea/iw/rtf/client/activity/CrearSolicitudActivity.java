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
package co.edu.udea.iw.rtf.client.activity;

import java.util.List;

import co.edu.udea.iw.rtf.client.ClientFactory;
import co.edu.udea.iw.rtf.client.SolicitudesService;
import co.edu.udea.iw.rtf.client.dto.ProductoListado;
import co.edu.udea.iw.rtf.client.dto.SolicitudCreado;
import co.edu.udea.iw.rtf.client.dto.SucursalListado;
import co.edu.udea.iw.rtf.client.dto.TipoSolicitudListado;
import co.edu.udea.iw.rtf.client.place.CrearSolicitudPlace;
import co.edu.udea.iw.rtf.client.ui.CrearSolicitudView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget. Esta actividad premite manejar la lÃ³gica de la
 * presentacion de la vista usada para permitir al cliente realizar una
 * solicitud a la empresa.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 */
public class CrearSolicitudActivity extends AbstractActivity implements
		CrearSolicitudView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController. Alternatively, could be
	 * injected via GIN. ClientFactory injectada para manejar la lÃ³gica de
	 * navegaciÃ³n de la aplicaciÃ³n web.
	 */
	private ClientFactory clientFactory;

	/**
	 * Crea un nuevo Activity para crear una nueva solicitud.
	 * 
	 * @param place
	 *            el place asociado con la activity, contiene la informaciÃ³n
	 *            necesaria para inicializar la vista asociada con el Activity.
	 * @param clientFactory
	 *            Fabrica que se encarga de manejar las diferentes vistas de la
	 *            aplicaciÃ³n web.
	 * */
	public CrearSolicitudActivity(CrearSolicitudPlace place,
			ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	/**
	 * MÃ©todo llamado cada vez que se incializa la actividad.
	 * */
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		view.setPresenter(this);
		this.bind();
		containerWidget.setWidget(view.asWidget());
	}

	/**
	 * MÃ©todo usado para unir los componentes de la actividad. En este mÃ©todo
	 * se realizan las inicializaciones y se usan los servicios remotos
	 * requeridos por la vista.
	 * */
	public void bind() {
		final CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		view.getListBoxProducto().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				Boolean otroProductoVisible = view.getListBoxProducto()
						.getSelectedIndex() == 0;
				view.getTextBoxOtroProducto().setVisible(otroProductoVisible);
				if(!otroProductoVisible)
				{
					view.getTextBoxOtroProducto().setText("");
				}
			}
		});
		// obtiene lista de productos
		SolicitudesService.Util.getInstance().getProductos(
				new AsyncCallback<List<ProductoListado>>() {

					@Override
					public void onSuccess(List<ProductoListado> result) {
						view.getListBoxProducto().clear();
						view.getListBoxProducto().addItem("Lista de productos");
						for (ProductoListado producto : result) {
							addProducto(producto);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});
		// obtiene lista de sucursales
		SolicitudesService.Util.getInstance().getSucursales(
				new AsyncCallback<List<SucursalListado>>() {

					@Override
					public void onSuccess(List<SucursalListado> result) {
						view.getListBoxSucursal().clear();
						view.getListBoxSucursal()
								.addItem("Lista de sucursales");
						for (SucursalListado sucursal : result) {
							addSucursal(sucursal);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
					
					}
				});
		// obtiene lista de tipos de solicitud
		SolicitudesService.Util.getInstance().getTiposSolicitud(
				new AsyncCallback<List<TipoSolicitudListado>>() {

					@Override
					public void onSuccess(List<TipoSolicitudListado> result) {

						view.getListBoxTipoSolicitud().clear();
						view.getListBoxTipoSolicitud().addItem(
								"Lista de solicitudes");
						for (TipoSolicitudListado tipoSolicitud : result) {
							addTipoSolicitud(tipoSolicitud);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						
					}
				});
	}

	/**
	 * MÃ©todo usado para agregar un nuevo tipo de solicitud en la lista de
	 * solicitudes incluida dentro de la vista.
	 * 
	 * @param tipoSolicitudListado
	 *            la informaciÃ³n requerida del tipo de solicitud.
	 * */
	public void addTipoSolicitud(TipoSolicitudListado tipoSolicitudListado) {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		ListBox listBoxTiposSolicitud = view.getListBoxTipoSolicitud();
		StringBuilder sb = new StringBuilder(tipoSolicitudListado.getCodigo()
				.toString());
		sb.append(". ").append(tipoSolicitudListado.getNombre());
		listBoxTiposSolicitud.addItem(sb.toString());
	}

	/**
	 * MÃ©todo usado para agregar una nueva sucursal en la lista de sucursales
	 * incluida dentro de la vista.
	 * 
	 * @param sucursalListado
	 *            la informaciÃ³n requerida de la sucursal.
	 * */
	public void addSucursal(SucursalListado sucursalListado) {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		ListBox listBoxSucursales = view.getListBoxSucursal();
		StringBuilder sb = new StringBuilder(sucursalListado.getCodigo()
				.toString());
		sb.append(". ").append(sucursalListado.getNombre());
		listBoxSucursales.addItem(sb.toString());
	}

	public Long getCodigoFromString(String stringCodigo) {
		if (stringCodigo == null) {
			return null;
		}
		return Long.parseLong(stringCodigo.substring(0,
				stringCodigo.indexOf(".")));
	}

	/**
	 * MÃ©todo usado para agregar un nuevo producto en la lista de productos
	 * incluida dentro de la vista.
	 * 
	 * @param productoListado
	 *            la informaciÃ³n requerida del producto.
	 * */
	public void addProducto(ProductoListado productoListado) {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		ListBox listBoxProductos = view.getListBoxProducto();
		StringBuilder sb = new StringBuilder(productoListado.getCodigo()
				.toString());
		sb.append(". ").append(productoListado.getNombre());
		listBoxProductos.addItem(sb.toString());
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see CrearSolicitudView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	/**
	 * M�todo para saber si el objeto es vaci� o null
	 * 
	 * @param s
	 * @return
	 */
	public Boolean isTextoVacio(Object s) {
		return s == null || "".equals(s);
	}

	/**
	 * M�todo para validar los campos, que no sea vaci�
	 * 
	 * @return
	 */

	public boolean validarCampos() {
		boolean validar = true;
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		if (isTextoVacio(view.getTextBoxNombresSolicitante().getText())) {
			view.getLabelNombresSolicitanteError().setText(
					"El campo nombres es obligatorio.");
			validar = false;
		}
		if (isTextoVacio(view.getTextBoxApellidosSolicitante().getText())) {
			view.getLabelApellidosSolicitanteError().setText(
					"El campo apellidos es obligatorio.");
			validar = false;
		}
		if (isTextoVacio(view.getTextBoxCorreoElectronico().getText())) {
			view.getLabelCorreoElectronicoError().setText(
					"El correo eletronico es invÃ¡lido.");
			validar = false;
		}
		if (!view.getTextBoxConfirmacionCorreo().getText()
				.equals(view.getTextBoxCorreoElectronico().getText())) {
			view.getLabelConfirmacionCorreoError()
					.setText(
							"El correo electronico de confirmacion no coincide con el correo original.");
			validar = false;
		}
		if (isTextoVacio(view.getTextBoxTelefono().getText())) {
			view.getLabelTelefonoError().setText("El telefono es obligatorio.");
			validar = false;
		}
		if (isTextoVacio(view.getTextBoxTextoSolicitud().getText())) {
			view.getLabelTextSolicitudError().setText(
					"El campo de texto de la solicitud es obligatorio.");
			validar = false;
		}
		if (view.getListBoxProducto().getSelectedIndex() == 0
				&& isTextoVacio(view.getTextBoxOtroProducto().getText())) {
			view.getLabelProductoError()
					.setText(
							"Debe seleccionar un producto de la lista o especificar otro producto.");
			validar = false;
		}
		if (view.getListBoxSucursal().getSelectedIndex() == 0) {
			view.getLabelSucursalError().setText(
					"Debe seleccionar una sucursal de la lista.");
			validar = false;
		}
		if (view.getListBoxTipoSolicitud().getSelectedIndex() == 0) {
			view.getLabelTipoSolicitudError().setText(
					"Debe seleccionar una solicitud de la lista.");
			validar = false;
		}
		return validar;
	}

	/**
	 * Método para validar letras Nombres y Apellidos
	 * 
	 * @param nombres los nombres ingresados por el cliente.
	 * @param apellidos los apellidos ingresados por el cliente.
	 * @return true si los apellidos y nombres del cliente son
	 * validos, false en caso contrario.
	 */
	public Boolean validarLetras(String nombres, String apellidos) {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		Boolean valido = Boolean.TRUE;
		if (!nombres.matches("^([A-Za-zñáéíóú ])+$")) {
			view.getLabelNombresSolicitanteError().setText(
					"Estimado Cliente.\n\n"
							+ "El nombre debe contener solo letras.");
			valido = Boolean.FALSE;
		}
		if (!apellidos.matches("^([A-Za-zñáéíóú ])+$")) {
			view.getLabelApellidosSolicitanteError().setText(
					"Estimado Cliente.\n\n"
							+ "El apellido debe contener solo letras.");
			valido = Boolean.FALSE;
		}

		return valido;
	}

	/**
	 * Método para validar los números Teléfono y Celular...
	 * 
	 * @param tel telefono ingresado por el cliente.
	 * @param cel celular ingresado por el cliente.
	 * @return true si el celular y el telefono del usuario son validos,
	 * false en caso contrario.
	 */
	public Boolean validarNumeros(String tel, String cel) {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		Boolean valido = Boolean.TRUE;
		if (!tel.matches("^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$")) {
			view.getLabelTelefonoError()
					.setText(
							"Estimado Cliente.\n\n"
									+ "El Tel\u00e9fono debe contener solo n\u00FAmeros, sin espacios.");
			valido = Boolean.FALSE;
		}
		if (!cel.matches("^\\d*$")) {
			view.getLabelCelularError()
					.setText(
							"Estimado Cliente.\n\n"
									+ "El Celular debe contener solo n\u00FAmeros, sin espacios. O vac\u00edo");
			valido = Boolean.FALSE;
		}

		return valido;
	}

	/**
	 * Método para validar la dirección de correo electrónico
	 * 
	 * @param correo el correo del cliente.
	 * @return true si el correo es valido, false en caso contrario.
	 */
	public Boolean validarCorreo(String correo) {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		if (!correo
				.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
			view.getLabelCorreoElectronicoError().setText(
					"Estimado Cliente.\n\n"
							+ "La direcci\u00f3n de correo no es v\u00e1lida\n"
							+ "Ejemplo: usuario@dominio.com");
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * Metodo que permite borrar el texto escrito en los labels usados para
	 * mostrar los errores dentro de la vista.
	 * */
	public void clearErrors() {
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		view.getLabelApellidosSolicitanteError().setText("");
		view.getLabelNombresSolicitanteError().setText("");
		view.getLabelCorreoElectronicoError().setText("");
		view.getLabelConfirmacionCorreoError().setText("");
		view.getLabelCelularError().setText("");
		view.getLabelTelefonoError().setText("");
		view.getLabelOtroProductoError().setText("");
		view.getLabelProductoError().setText("");
		view.getLabelSucursalError().setText("");
		view.getLabelTipoSolicitudError().setText("");
		view.getLabelTextSolicitudError().setText("");
	}

	/**
	 * Metodo usado para poner en blanco los campos
	 * del formulario.
	 * */
	public void limpiar() {
		clearErrors();
		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		view.getTextBoxOtroProducto().setVisible(Boolean.TRUE);
		view.getTextBoxNombresSolicitante().setText("");
		view.getTextBoxApellidosSolicitante().setText("");
		view.getTextBoxCorreoElectronico().setText("");
		view.getTextBoxConfirmacionCorreo().setText("");
		view.getTextBoxTelefono().setText("");
		view.getTextBoxCelular().setText("");
		view.getTextBoxTextoSolicitud().setText("");
		view.getListBoxProducto().setItemSelected(0, true);
		view.getListBoxSucursal().setItemSelected(0, true);
		view.getListBoxTipoSolicitud().setItemSelected(0, true);
		view.getTextBoxOtroProducto().setText("");
	}

	/**
	 * MÃ©todo usado para guardar una solicitud en la base de datos y comprobar
	 * que los datos ingresados por el usuario son correctos.
	 * */
	@Override
	public void guardarSolicitud() {

		CrearSolicitudView view = clientFactory.getCrearSolicitudView();
		this.clearErrors();
		if (validarCampos()) {
			if (validarLetras(view.getTextBoxNombresSolicitante().getText(),
					view.getTextBoxApellidosSolicitante().getText())) {
				if (validarNumeros(view.getTextBoxTelefono().getText(), view
						.getTextBoxCelular().getText())) {
					if (validarCorreo(view.getTextBoxCorreoElectronico()
							.getText())) {

						SolicitudCreado solicitudCreado = new SolicitudCreado();
						solicitudCreado.setNombres(view
								.getTextBoxNombresSolicitante().getText());
						solicitudCreado.setApellidos(view
								.getTextBoxApellidosSolicitante().getText());
						solicitudCreado.setCorreoElectronico(view
								.getTextBoxCorreoElectronico().getText());
						solicitudCreado.setConfirmacionCorreo(view
								.getTextBoxConfirmacionCorreo().getText());
						solicitudCreado.setCelular(view.getTextBoxCelular()
								.getText());
						solicitudCreado.setOtroProducto(view
								.getTextBoxOtroProducto().getText());
						solicitudCreado.setTelefono(view.getTextBoxTelefono()
								.getText());
						solicitudCreado.setTextoSolicitud(view
								.getTextBoxTextoSolicitud().getText());
						solicitudCreado.setProducto(this
								.getCodigoFromString(view.getListBoxProducto()
										.getItemText(
												view.getListBoxProducto()
														.getSelectedIndex())));
						solicitudCreado.setSucursal(this
								.getCodigoFromString(view.getListBoxSucursal()
										.getItemText(
												view.getListBoxSucursal()
														.getSelectedIndex())));
						solicitudCreado.setTipoSolicitud(this
								.getCodigoFromString(view
										.getListBoxTipoSolicitud().getItemText(
												view.getListBoxTipoSolicitud()
														.getSelectedIndex())));

						final DialogBox dialogBox = view.getDialogBox();
						SolicitudesService.Util.getInstance().guardarSolicitud(
								solicitudCreado, new AsyncCallback<String>() {

									@Override
									public void onSuccess(String result) {
										dialogBox.setText(result);
										dialogBox.center();

									}

									@Override
									public void onFailure(Throwable caught) {
										dialogBox
												.setText("Pedimos disculpas, su solicitud no ha podido ser procesada, intente mas tarde.");
										dialogBox.center();
									}
								});
					}
				}

			}

		}
	}

	/**
	 * @see CrearSolicitudView.Presenter#limpiarFormulario()
	 * */
	@Override
	public void limpiarFormulario() {
		// TODO Auto-generated method stub
		limpiar();
		
	}
}
