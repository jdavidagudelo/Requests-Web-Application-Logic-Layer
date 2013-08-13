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

import java.util.List;

import co.edu.udea.iw.rtf.client.dto.ProductoListado;
import co.edu.udea.iw.rtf.client.dto.SolicitudCreado;
import co.edu.udea.iw.rtf.client.dto.SolicitudListado;
import co.edu.udea.iw.rtf.client.dto.SucursalListado;
import co.edu.udea.iw.rtf.client.dto.TipoSolicitudListado;
import co.edu.udea.iw.rtf.client.dto.UsuarioListado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interfaz usada para acceder a los servicios de la lógica del negocio desde la
 * vista de la aplicación web.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 * */
@RemoteServiceRelativePath("SolicitudesService")
public interface SolicitudesService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static SolicitudesServiceAsync instance;

		public static SolicitudesServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(SolicitudesService.class);
			}
			return instance;
		}
	}

	/**
	 * Método usado para comprobar si los datos de un usuario que se registra en
	 * el sistema son correctos.
	 * 
	 * @param login
	 *            El login del usuario.
	 * @param clave
	 *            la clave del usuario
	 * @return True si los datos del usuario son validos, false en caso
	 *         contrario.
	 * */
	public Boolean testUser(String login, String clave);

	/**
	 * Método que permite crear una nueva solicitud en la base de datos.
	 * 
	 * @param solicitudCreado
	 *            La información requerida para crear la nueva solicitud en la
	 *            base de datos.
	 * @return String indicando el resultado de la operación de inserción.
	 * */
	public String guardarSolicitud(SolicitudCreado solicitudCreado);

	/**
	 * Método que permite obtener la lista de usuarios de la base de datos.
	 * 
	 * @return Lista de usuarios de la base de datos.
	 * */
	public List<UsuarioListado> getUsuarios();

	/**
	 * Método que permite obtener la lista de productos de la base de datos.
	 * 
	 * @return Lista de productos de la base de datos.
	 * */
	public List<ProductoListado> getProductos();

	/**
	 * Lista de sucursales de la base de datos.
	 * 
	 * @return Lista de sucursales de la base de datos.
	 * */
	public List<SucursalListado> getSucursales();

	/**
	 * Lista de tipos de solicitudes de la base de datos.
	 * 
	 * @return Lista de tipos de solicituides de la base de datos.
	 * */
	public List<TipoSolicitudListado> getTiposSolicitud();

	/**
	 * Lista de solicitude de la base de datos.
	 * 
	 * @return La lista de todas las solicitudes existentes en la base de datos.
	 * */
	public List<SolicitudListado> getSolicitudes();

	/**
	 * Lista de las solicitudes asignadas a un usuario.
	 * 
	 * @param loginUsuario
	 *            el login del usuario al que se desean consultar las
	 *            solicitudes asignadas.
	 * */
	public List<SolicitudListado> getSolicitudesAsignadas(String loginUsuario);

	/**
	 * Método que permite asignar una solicitud a un usuario de la empresa.
	 * 
	 * @param codigoSolicitud
	 *            el código de la solicitud que se desea asignar.
	 * @param loginUsuario
	 *            el login del usuario al que se le desea asignar la solcitud.
	 * */
	public void asignarSolicitud(Long codigoSolicitud, String loginUsuario);

	/**
	 * Método usado para establecer si el usuario con el login ingresado tiene
	 * permisos de administrador.
	 * */
	public Boolean isAdministrador(String login);
}
