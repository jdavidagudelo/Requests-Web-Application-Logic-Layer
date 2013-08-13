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

import co.edu.udea.iw.rtf.client.ClientFactory;
import co.edu.udea.iw.rtf.client.SolicitudesService;
import co.edu.udea.iw.rtf.client.place.LoginPlace;
import co.edu.udea.iw.rtf.client.place.SolicitudesPlace;
import co.edu.udea.iw.rtf.client.ui.LoginView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget. Actividad que permite al usuario loguearse en el sistema
 * para realizar cambios que requieren autenticación.
 * 
 */
public class LoginActivity extends AbstractActivity implements
		LoginView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController. Alternatively, could be
	 * injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
	private String name;

	/**
	 * Crea un nuevo Activity para loguearse.
	 * 
	 * @param place
	 *            el place asociado con la activity, contiene la información
	 *            necesaria para inicializar la vista asociada con el Activity.
	 * @param clientFactory
	 *            Fabrica que se encarga de manejar las diferentes vistas de la
	 *            aplicación web.
	 * */
	public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
		this.name = place.getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		LoginView view = clientFactory.getLoginView();
		view.setName(name);
		view.setPresenter(this);
		this.bind();
		containerWidget.setWidget(view.asWidget());
	}

	public void bind() {

	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see LoginView.Presenter#goTo(Place)
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
		LoginView view = clientFactory.getLoginView();
		if (isTextoVacio(view.getTextBoxLogin().getText())
				|| isTextoVacio(view.getTextBoxClave().getText())) {
			Window.alert("Nombre de usuario o Contrase�a vac�o");
			validar = false;
		}
		return validar;
	}

	/**
	 * Método usado para validar un usuario que desea loguearse en el sistema.
	 * 
	 * @return true si el usuario tiene permisos de acceso, false en caso
	 *         contrario.
	 * */
	@Override
	public Boolean testUsuario() {
		if (validarCampos()) {

			LoginView view = clientFactory.getLoginView();
			PasswordTextBox textBoxClave = view.getTextBoxClave();
			TextBox textBoxLogin = view.getTextBoxLogin();
			final String clave = textBoxClave.getText();
			final String login = textBoxLogin.getText();
			SolicitudesService.Util.getInstance().testUser(login, clave,
					new AsyncCallback<Boolean>() {

						@Override
						public void onSuccess(Boolean result) {
							if (result) {
								Cookies.setCookie("login", login);
								goTo(new SolicitudesPlace(login));
							} else {
								Window.alert("Usuario o contraseña invalidos por favor intente de nuevo.");
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Usuario o contraseña no validos");
						}
					});
		}

		return null;

	}
}
