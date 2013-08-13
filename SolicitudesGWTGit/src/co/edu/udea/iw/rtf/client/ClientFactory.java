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
import co.edu.udea.iw.rtf.client.ui.CrearSolicitudView;
import co.edu.udea.iw.rtf.client.ui.LoginView;
import co.edu.udea.iw.rtf.client.ui.MainPageView;
import co.edu.udea.iw.rtf.client.ui.SolicitudesView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

/**
 * ClientFactory helpful to use a factory or dependency injection framework like
 * GIN to obtain references to objects needed throughout your application like
 * the {@link EventBus}, {@link PlaceController} and views. Fabrica usada por la
 * aplicacion para crear las vistas.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 */
public interface ClientFactory {
	public EventBus getEventBus();

	public PlaceController getPlaceController();

	/**
	 * Vista de las solicitudes que puede ver el gerente de cuentas. Permite al
	 * usuario asignar un responsable a las solicitudes pendientes.
	 * 
	 * @return Vista de la lista de solicitudes con una opción para seleccionar
	 *         y asignar el responsable de una solicitud.
	 * */
	public SolicitudesView getSolicitudesView();

	/**
	 * Vista que permite a un cliente ingresar una solicitud.
	 * 
	 * @return Vista que permite a un cliente ingresar una nueva solicitud.
	 * */
	public CrearSolicitudView getCrearSolicitudView();

	/**
	 * Vista que permite a un cliente loguearse en el sistema para realizar
	 * modificaciones en la base de datos.
	 * 
	 * @return La vista del usuario para loguearse en el sistema.
	 * */
	public LoginView getLoginView();

	/**
	 * Página principal de la aplicación web.
	 * 
	 * @return Página principal de la aplicación web.
	 * */
	public MainPageView getMainPageView();

	/**
	 * Vista usada para permitir al usuario asignar el responsable
	 * de responder una solicitud.
	 * @return vista para asignar solicitud a un responsable.
	 * */
	public AsignarSolicitudView getAsignarSolicitudView();
}
