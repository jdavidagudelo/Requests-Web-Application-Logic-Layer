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

import co.edu.udea.iw.rtf.client.dto.UsuarioSingleton;
import co.edu.udea.iw.rtf.client.place.CrearSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.SolicitudesPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link MainPageView}.
 * @author Juan David Agudelo jdaaa2009@gmail.com
 */
public class MainPageViewImpl extends Composite implements MainPageView {

	interface Binder extends UiBinder<Widget, MainPageViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;

	public MainPageViewImpl() {
		initWidget(binder.createAndBindUi(this));
		buttonLogin.addStyleName("button");
		buttonRealizarSolicitud.addStyleName("button");
	}

	@UiField
	Button buttonLogin;
	@UiField
	Button buttonRealizarSolicitud;

	@UiHandler("buttonRealizarSolicitud")
	void onButtonRealizarSolicitudClick(ClickEvent event) {
		listener.goTo(new CrearSolicitudPlace(""));
	}

	@UiHandler("buttonLogin")
	void onButtonLoginClick(ClickEvent event) {
		String login = UsuarioSingleton.getInstance().getLogin();
		listener.goTo(new SolicitudesPlace(login));
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public Button getButtonAsignarSolicitud() {
		return buttonLogin;
	}

}
