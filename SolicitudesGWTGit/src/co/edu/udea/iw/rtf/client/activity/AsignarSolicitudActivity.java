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
import co.edu.udea.iw.rtf.client.dto.UsuarioListado;
import co.edu.udea.iw.rtf.client.dto.UsuarioSingleton;
import co.edu.udea.iw.rtf.client.place.AsignarSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.SolicitudesPlace;
import co.edu.udea.iw.rtf.client.ui.AsignarSolicitudView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget. Actividad asociada con la vista utilizada para permitir a
 * un usuario asignar el responsable de responder una solicitud.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 */
public class AsignarSolicitudActivity extends AbstractActivity implements
		AsignarSolicitudView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController. Alternatively, could be
	 * injected via GIN.
	 */
	private ClientFactory clientFactory;
	/**
	 * La solicitud que se desea asignar actualmente dentro de esta actividad.
	 * */
	private SolicitudListado solicitudListado;
	/**
	 * Sample property.
	 */
	private String login;

	public AsignarSolicitudActivity(AsignarSolicitudPlace place,
			ClientFactory clientFactory) {
		this.login = place.getLogin();
		this.solicitudListado = place.getSolicitudListado();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		AsignarSolicitudView view = clientFactory.getAsignarSolicitudView();
		view.setPresenter(this);
		this.bind();
		containerWidget.setWidget(view.asWidget());
	}

	/**
	 * Método usado para inicializar la vista asociada con esta actividad.
	 * */
	public void bind() {
		if (solicitudListado == null) {
			return;
		}
		AsignarSolicitudView view = clientFactory.getAsignarSolicitudView();
		view.getListBoxUsuario().clear();
		view.getTextBoxCelular().setText(solicitudListado.getCelular());
		view.getTextBoxCorreoElectronico().setText(
				solicitudListado.getCorreoElectronico());
		view.getTextBoxNombreCompleto().setText(
				solicitudListado.getNombreCompletoSolicitante());
		view.getTextBoxNombreProducto().setText(
				solicitudListado.getNombreProducto());
		view.getTextBoxNombreSucursal().setText(
				solicitudListado.getNombreSucursal());
		view.getTextBoxNombreTipoSolicitud().setText(
				solicitudListado.getNombreTipoSolicitud());
		view.getTextBoxTelefono().setText(solicitudListado.getTelefono());
		view.getTextBoxTextoSolicitud().setText(
				solicitudListado.getTextoSolicitud());
		final ListBox listBoxUsuarios = view.getListBoxUsuario();
		// llena la lista de los usuarios disponibles para ser asignados
		// para responder una solicitud
		SolicitudesService.Util.getInstance().getUsuarios(
				new AsyncCallback<List<UsuarioListado>>() {
					@Override
					public void onSuccess(List<UsuarioListado> result) {
						listBoxUsuarios.clear();
						listBoxUsuarios.addItem("Lista de usuarios");
						for (UsuarioListado usuarioListado : result) {
							listBoxUsuarios.addItem(usuarioListado.getLogin());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see AsignarSolicitudView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	/**
	 * @see AsignarSolicitudView.Presenter#asignarSolicitud()
	 * */
	@Override
	public void asignarSolicitud() {
		if (solicitudListado == null) {
			return;
		}
		AsignarSolicitudView view = clientFactory.getAsignarSolicitudView();
		final ListBox listBoxUsuarios = view.getListBoxUsuario();
		if (listBoxUsuarios.getSelectedIndex() == 0) {
			view.getLabelusuarioError().setText(
					"Debe seleccionar un usuario de la lista.");
			return;
		}
		SolicitudesService.Util.getInstance()
				.asignarSolicitud(
						solicitudListado.getCodigo(),
						listBoxUsuarios.getItemText(listBoxUsuarios
								.getSelectedIndex()),
						new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								String login = UsuarioSingleton.getInstance()
										.getLogin();
								goTo(new SolicitudesPlace(login));
							}

							@Override
							public void onFailure(Throwable caught) {

							}
						});
	}

	/**
	 * @see AsignarSolicitudView.Presenter#cancelarAsignacionSolicitud()
	 * */
	@Override
	public void cancelarAsignacionSolicitud() {
		String login = UsuarioSingleton.getInstance().getLogin();
		this.goTo(new SolicitudesPlace(login));
	}
}
