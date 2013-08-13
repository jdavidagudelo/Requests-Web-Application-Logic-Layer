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
package co.edu.udea.iw.rtf.server;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.iw.rtf.client.SolicitudesService;
import co.edu.udea.iw.rtf.client.dto.ProductoListado;
import co.edu.udea.iw.rtf.client.dto.SolicitudCreado;
import co.edu.udea.iw.rtf.client.dto.SolicitudListado;
import co.edu.udea.iw.rtf.client.dto.SucursalListado;
import co.edu.udea.iw.rtf.client.dto.TipoSolicitudListado;
import co.edu.udea.iw.rtf.client.dto.UsuarioListado;
import co.edu.udea.iw.rtf.dto.Producto;
import co.edu.udea.iw.rtf.dto.Solicitud;
import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.service.ProductoService;
import co.edu.udea.iw.rtf.service.SolicitudService;
import co.edu.udea.iw.rtf.service.SucursalService;
import co.edu.udea.iw.rtf.service.TipoSolicitudService;
import co.edu.udea.iw.rtf.service.UsuarioService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
/**
 * Implementacion de los servicios web requeridos para el
 * funcionamiento de la aplicacion Web.
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * */
public class SolicitudesServiceImpl extends SpringRemoteServiceServlet
		implements SolicitudesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see SolicitudesService#getSolicitudes()
	 * */
	@Override
	public List<SolicitudListado> getSolicitudes() {
		SolicitudService solicitudService = this.getSolicitudService();
		List<SolicitudListado> solicitudes = new ArrayList<SolicitudListado>();
		try {
			for (Solicitud solicitud : solicitudService
					.obtenerSolicitudesPendientes()) {
				SolicitudListado lista = new SolicitudListado();
				lista.setCodigo(solicitud.getCodigo());
				lista.setCelular(solicitud.getCelular());
				lista.setTelefono(solicitud.getTelefono());
				lista.setCorreoElectronico(solicitud.getCorreoElectronico());
				lista.setFechaSolicitud(solicitud.getFechaSolicitud());
				StringBuilder sb = new StringBuilder(
						solicitud.getNombresSolicitante());
				sb.append(" ").append(solicitud.getApellidosSolicitante());
				lista.setNombreCompletoSolicitante(sb.toString());
				lista.setNombreProducto(solicitud.getProducto().getNombre());
				lista.setNombreSucursal(solicitud.getSucursal().getNombre());
				lista.setNombreTipoSolicitud(solicitud.getTipoSolicitud()
						.getNombre());
				lista.setTextoSolicitud(solicitud.getTextoSolicitud());

				solicitudes.add(lista);
			}
		} catch (IWDaoException e) {
		}
		return solicitudes;
	}

	/**
	 * @see SolicitudesService#testUser(String, String)
	 * */
	@Override
	public Boolean testUser(String usuarioLogin, String clave) {
		UsuarioService usuarioService = this.getUsuarioService();
		try {
			return usuarioService.validar(usuarioLogin, clave);
		} catch (IWDaoException e) {
		}
		return Boolean.FALSE;
	}

	/**
	 * @see SolicitudesService#getProductos()
	 * */
	@Override
	public List<ProductoListado> getProductos() {
		ProductoService productoService = this.getProductoService();
		List<ProductoListado> productos = new ArrayList<>();
		try {
			for (Producto producto : productoService.obtener()) {
				ProductoListado productoListado = new ProductoListado();
				productoListado.setCodigo(producto.getCodigo());
				productoListado.setNombre(producto.getNombre());
				productos.add(productoListado);
			}
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productos;
	}

	/**
	 * @see SolicitudesService#getSucursales()
	 * */
	@Override
	public List<SucursalListado> getSucursales() {
		SucursalService sucursalService = this.getSucursalService();
		List<SucursalListado> sucursales = new ArrayList<>();
		try {
			for (Sucursal sucursal : sucursalService.obtener()) {
				SucursalListado sucursalListado = new SucursalListado();
				sucursalListado.setCodigo(sucursal.getCodigo());
				sucursalListado.setNombre(sucursal.getNombre());
				sucursales.add(sucursalListado);
			}
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sucursales;
	}

	/**
	 * @see SolicitudesService#getTiposSolicitud()
	 * */
	@Override
	public List<TipoSolicitudListado> getTiposSolicitud() {
		TipoSolicitudService tipoSolicitudService = this
				.getTipoSolicitudService();
		List<TipoSolicitudListado> tiposSolicitud = new ArrayList<>();
		try {
			for (TipoSolicitud tipoSolicitud : tipoSolicitudService.obtener()) {
				TipoSolicitudListado tipoSolicitudListado = new TipoSolicitudListado();
				tipoSolicitudListado.setCodigo(tipoSolicitud.getCodigo());
				tipoSolicitudListado.setNombre(tipoSolicitud.getNombre());
				tiposSolicitud.add(tipoSolicitudListado);
			}
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tiposSolicitud;
	}

	/**
	 * @see SolicitudesService#guardarSolicitud(SolicitudCreado)
	 * */
	@Override
	public String guardarSolicitud(SolicitudCreado solicitudCreado) {
		SolicitudService solicitudService = this.getSolicitudService();
		String retorno = "Solicitud guardada exitosamente.";
		try {
			solicitudService.guardarSolicitud(solicitudCreado.getNombres(),
					solicitudCreado.getApellidos(),
					solicitudCreado.getCorreoElectronico(),
					solicitudCreado.getConfirmacionCorreo(),
					solicitudCreado.getTelefono(),
					solicitudCreado.getCelular(),
					solicitudCreado.getSucursal(),
					solicitudCreado.getProducto(),
					solicitudCreado.getTextoSolicitud(),
					solicitudCreado.getTipoSolicitud(),
					solicitudCreado.getOtroProducto());
		} catch (IWDaoException e) {
			retorno = e.getMessage();
		} catch (NullPointerException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	/**
	 * @see SolicitudesService#getSolicitudesAsignadas(String)
	 * */
	@Override
	public List<SolicitudListado> getSolicitudesAsignadas(String loginUsuario) {
		SolicitudService solicitudService = this.getSolicitudService();
		List<SolicitudListado> solicitudes = new ArrayList<SolicitudListado>();
		try {
			for (Solicitud solicitud : solicitudService
					.obtenerSolicitudesAsignadasPendientes(loginUsuario)) {
				SolicitudListado lista = new SolicitudListado();
				lista.setTelefono(solicitud.getTelefono());
				lista.setCodigo(solicitud.getCodigo());
				lista.setCelular(solicitud.getCelular());
				lista.setCorreoElectronico(solicitud.getCorreoElectronico());
				lista.setFechaSolicitud(solicitud.getFechaSolicitud());
				StringBuilder sb = new StringBuilder(
						solicitud.getNombresSolicitante());
				sb.append(" ").append(solicitud.getApellidosSolicitante());
				lista.setNombreCompletoSolicitante(sb.toString());
				lista.setNombreProducto(solicitud.getProducto().getNombre());
				lista.setNombreSucursal(solicitud.getSucursal().getNombre());
				lista.setNombreTipoSolicitud(solicitud.getTipoSolicitud()
						.getNombre());
				lista.setTextoSolicitud(solicitud.getTextoSolicitud());
				solicitudes.add(lista);
			}
		} catch (IWDaoException e) {
		}
		return solicitudes;
	}

	/**
	 * @see SolicitudesService#getUsuarios()
	 * */
	@Override
	public List<UsuarioListado> getUsuarios() {
		UsuarioService usuarioService = this.getUsuarioService();
		List<UsuarioListado> usuariosListado = new ArrayList<>();
		try {
			for (Usuario usuario : usuarioService.obtener()) {
				UsuarioListado usuarioListado = new UsuarioListado();
				usuarioListado.setLogin(usuario.getLogin());
				usuariosListado.add(usuarioListado);
			}
		} catch (IWDaoException e) {

		}
		return usuariosListado;
	}

	/**
	 * @see SolicitudesService#asignarSolicitud(Long, String)
	 * */
	@Override
	public void asignarSolicitud(Long codigoSolicitud, String loginUsuario) {
		SolicitudService solicitudService = this.getSolicitudService();
		try {
			solicitudService.asignarSolicitud(codigoSolicitud, loginUsuario);
		} catch (IWDaoException e) {
		}
	}

	/**
	 * @see SolicitudesService#isAdministrador(String)
	 * */
	@Override
	public Boolean isAdministrador(String login) {
		UsuarioService usuarioService = this.getUsuarioService();
		try {
			return usuarioService.isAdministrador(login);
		} catch (IWDaoException e) {
		}
		return Boolean.FALSE;
	}
}
