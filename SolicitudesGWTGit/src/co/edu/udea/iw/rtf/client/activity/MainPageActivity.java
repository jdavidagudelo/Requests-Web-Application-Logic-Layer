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
import co.edu.udea.iw.rtf.client.dto.UsuarioSingleton;
import co.edu.udea.iw.rtf.client.place.MainPagePlace;
import co.edu.udea.iw.rtf.client.ui.MainPageView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget. Actividad asociada con la página inicial de la aplicación.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 */
public class MainPageActivity extends AbstractActivity implements
		MainPageView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController. Alternatively, could be
	 * injected via GIN.
	 */
	private ClientFactory clientFactory;

	public MainPageActivity(MainPagePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		MainPageView view = clientFactory.getMainPageView();
		view.setPresenter(this);
		this.bind();
		containerWidget.setWidget(view.asWidget());
	}

	/**
	 * Método usado para inicializar la vista de la aplicación.
	 * */
	public void bind() {
		String login = UsuarioSingleton.getInstance().getLogin();
		MainPageView view = clientFactory.getMainPageView();
		// si el usuario no es usuario de la empresa no puede asignar o ver la
		// lista de solicitudes.
		view.getButtonAsignarSolicitud().setVisible(
				Boolean.valueOf(login != null));
		if(UsuarioSingleton.getInstance().getIsAdministrador())
		{
			view.getButtonAsignarSolicitud().setText("Asignar");
		}
		else
		{
			view.getButtonAsignarSolicitud().setText("Responder");
		}
	}

	@Override
	public String mayStop() {

		return null;
	}

	/**
	 * @see MainPageView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
