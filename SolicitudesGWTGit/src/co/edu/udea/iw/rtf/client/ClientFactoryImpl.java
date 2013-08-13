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
package co.edu.udea.iw.rtf.client;

import co.edu.udea.iw.rtf.client.ui.AsignarSolicitudView;
import co.edu.udea.iw.rtf.client.ui.AsignarSolicitudViewImpl;
import co.edu.udea.iw.rtf.client.ui.CrearSolicitudView;
import co.edu.udea.iw.rtf.client.ui.CrearSolicitudViewImpl;
import co.edu.udea.iw.rtf.client.ui.LoginView;
import co.edu.udea.iw.rtf.client.ui.LoginViewImpl;
import co.edu.udea.iw.rtf.client.ui.MainPageView;
import co.edu.udea.iw.rtf.client.ui.MainPageViewImpl;
import co.edu.udea.iw.rtf.client.ui.SolicitudesView;
import co.edu.udea.iw.rtf.client.ui.SolicitudesViewImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

/**
 * Sample implementation of {@link ClientFactory}.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 */
public class ClientFactoryImpl implements ClientFactory {
	/**
	 * EventBus usado para enviar información entre las vistas de la aplicación.
	 * */
	private static final EventBus eventBus = new SimpleEventBus();
	/**
	 * PlaceController usado para la navegación entre los distintos places de la
	 * aplicación de la aplicación web.
	 * */
	@SuppressWarnings("deprecation")
	private static final PlaceController placeController = new PlaceController(
			eventBus);
	/**
	 * Vista de la lista de solicitudes mostrada a un usuario de la empresa.
	 * */
	private static final SolicitudesView view = new SolicitudesViewImpl();
	/**
	 * Vista usada por un cliente de la empresa para crear una nueva solicitud.
	 * */
	private static final CrearSolicitudView crearSolicitudView = new CrearSolicitudViewImpl();
	/**
	 * Vista que permite al usuario loguearse en la página de la empresa.
	 * */
	private static final LoginView loginView = new LoginViewImpl();

	/**
	 * Vista incial de la aplicación web para un usuario registrado.
	 * */
	private static final MainPageView mainPageView = new MainPageViewImpl();

	/**
	 * Vista para permitir a un usuario asignar una solicitud.
	 * */
	private static final AsignarSolicitudView asignarSolicitudView = new AsignarSolicitudViewImpl();

	/**
	 * @see ClientFactory#getEventBus()
	 */
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	/**
	 * @see ClientFactory#getPlaceController()
	 * */
	@Override
	public PlaceController getPlaceController() {

		return placeController;
	}

	/**
	 * @see ClientFactory#getSolicitudesView()
	 * */
	@Override
	public SolicitudesView getSolicitudesView() {
		return view;
	}

	/**
	 * @see ClientFactory#getCrearSolicitudView()
	 * */
	@Override
	public CrearSolicitudView getCrearSolicitudView() {
		return crearSolicitudView;
	}

	/**
	 * @see ClientFactory#getLoginView()
	 * */
	@Override
	public LoginView getLoginView() {
		return loginView;
	}

	/**
	 * @see ClientFactory#getMainPageView()
	 * */
	@Override
	public MainPageView getMainPageView() {
		// TODO Auto-generated method stub
		return mainPageView;
	}

	/**
	 * @see ClientFactory#getAsignarSolicitudView()
	 * */
	@Override
	public AsignarSolicitudView getAsignarSolicitudView() {
		return asignarSolicitudView;
	}
}
