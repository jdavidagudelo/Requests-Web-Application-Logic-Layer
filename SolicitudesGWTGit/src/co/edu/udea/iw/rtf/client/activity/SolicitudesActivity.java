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
import co.edu.udea.iw.rtf.client.dto.SolicitudListado;
import co.edu.udea.iw.rtf.client.dto.UsuarioSingleton;
import co.edu.udea.iw.rtf.client.place.AsignarSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.SolicitudesPlace;
import co.edu.udea.iw.rtf.client.ui.SolicitudesView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget. Actividad utilizada para mostrar la lista de solicitudes de
 * acuerdo con los roles del usuario.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 */
public class SolicitudesActivity extends AbstractActivity implements
		SolicitudesView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController. Alternatively, could be
	 * injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
	private String login;

	/**
	 * Crea un nuevo Activity para listar todas las solicitudes pendientes.
	 * 
	 * @param place
	 *            el place asociado con la activity, contiene la información
	 *            necesaria para inicializar la vista asociada con el Activity.
	 * @param clientFactory
	 *            Fabrica que se encarga de manejar las diferentes vistas de la
	 *            aplicación web.
	 * */
	public SolicitudesActivity(SolicitudesPlace place,
			ClientFactory clientFactory) {
		this.login = place.getLogin();
		this.clientFactory = clientFactory;
		this.bind();
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {

		SolicitudesView view = clientFactory.getSolicitudesView();
		view.setLogin(login);
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
	}

	/**
	 * Metodo para borrar la tabla y cargar solo las solicitudes pendientes
	 */
	public void cleanTable() {

		final SolicitudesView view = clientFactory.getSolicitudesView();
		FlexTable tabla = view.getTable();
		tabla.clear();
		tabla.removeAllRows();
		tabla.setText(0, 0, "Nombre Completo");
		tabla.setText(0, 1, "Correo electronico");
		tabla.setText(0, 2, "Fecha de solicitud");
		tabla.setText(0, 3, "Tipo de solicitud");
		tabla.setText(0, 4, "Texto de la solicitud");
		tabla.getCellFormatter().setWidth(0, 0, "100px");
		tabla.getCellFormatter().setWidth(0, 1, "100px");
		tabla.getCellFormatter().setWidth(0, 2, "100px");
		tabla.getCellFormatter().setWidth(0, 3, "100px");
		tabla.getCellFormatter().setWidth(0, 4, "100px");
		tabla.getRowFormatter().addStyleName(0, "tablaHeader");
	}

	/**
	 * Crea la lista de solicitudes como una tabla.
	 * */
	public void bind() {
		// SolicitudesService.Util.getInstance().isAdministrador(login,
		// new AsyncCallback<Boolean>() {
		// @Override
		// public void onSuccess(final Boolean isAdministrador) {
		final Boolean isAdministrador = UsuarioSingleton.getInstance()
				.getIsAdministrador();
		if (isAdministrador) {
			SolicitudesService.Util.getInstance().getSolicitudes(
					new AsyncCallback<List<SolicitudListado>>() {

						@Override
						public void onSuccess(List<SolicitudListado> result) {
							cleanTable();
							for (SolicitudListado solicitud : result) {
								addSolicitud(solicitud, isAdministrador);
							}
						}

						@Override
						public void onFailure(Throwable caught) {
						}
					});
		} else {
			SolicitudesService.Util.getInstance().getSolicitudesAsignadas(
					login, new AsyncCallback<List<SolicitudListado>>() {

						@Override
						public void onSuccess(List<SolicitudListado> result) {
							cleanTable();
							for (SolicitudListado solicitud : result) {
								addSolicitud(solicitud, isAdministrador);
							}
						}

						@Override
						public void onFailure(Throwable caught) {
						}
					});
		}

		// @Override
		// public void onFailure(Throwable caught) {
		//
		// }
		// });
	}

	/**
	 * Agrega una nueva solcitud a la tabla de solicitudes.
	 * 
	 * @param solicitud
	 *            La información de la solicitud que se desea listar.
	 * */
	public void addSolicitud(final SolicitudListado solicitud,
			Boolean isAdministrador) {

		final SolicitudesView view = clientFactory.getSolicitudesView();
		FlexTable tabla = view.getTable();
		final int indice = tabla.getRowCount();
		tabla.setText(indice, 0, solicitud.getNombreCompletoSolicitante());
		tabla.setText(indice, 1, solicitud.getCorreoElectronico());
		tabla.setText(indice, 2, solicitud.getFechaSolicitud().toString());
		tabla.setText(indice, 3, solicitud.getNombreTipoSolicitud());
		tabla.setText(indice, 4, solicitud.getTextoSolicitud());
		tabla.getCellFormatter().setWidth(indice, 0, "100px");
		tabla.getCellFormatter().setWidth(indice, 1, "100px");
		tabla.getCellFormatter().setWidth(indice, 2, "100px");
		tabla.getCellFormatter().setWidth(indice, 3, "100px");
		tabla.getCellFormatter().setWidth(indice, 4, "100px");

		if (isAdministrador) {
			Button botonEliminar = new Button("Asignar");

			// el boton se agrega para permitir al usuario
			// asignar un
			// responsable a la solicitud.
			botonEliminar.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					cleanTable();
					goTo(new AsignarSolicitudPlace(login, solicitud));
				}
			});

			view.getTable().setWidget(indice, 7, botonEliminar);

		}
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see SolicitudesView.Presenter#goTo(Place) Método que permite ir a un
	 *      nuevo place.
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}