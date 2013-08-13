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

import co.edu.udea.iw.rtf.client.dto.UsuarioSingleton;
import co.edu.udea.iw.rtf.client.mvp.AppActivityMapper;
import co.edu.udea.iw.rtf.client.mvp.AppPlaceHistoryMapper;
import co.edu.udea.iw.rtf.client.place.AsignarSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.CrearSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.LoginPlace;
import co.edu.udea.iw.rtf.client.place.MainPagePlace;
import co.edu.udea.iw.rtf.client.place.SolicitudesPlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Punto de entrada al módulo principal de la aplicación web.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 * */
public class Solicitudes implements EntryPoint {

	private SimplePanel appWidget = new SimplePanel();
	private Place solicitudesPlace = new SolicitudesPlace("Go!");
	private Place crearSolicitudPlace = new CrearSolicitudPlace("Go!");
	private Place loginPlace = new LoginPlace("Go!");
	private Place mainPagePlace = new MainPagePlace("Go!");
	private Place asignarSolicitudPlace = new AsignarSolicitudPlace("Go!", null);

	@SuppressWarnings("deprecation")
	public void onModuleLoad() {
		Dictionary var = Dictionary.getDictionary("userInSession");
		UsuarioSingleton.setUpFromDictionary(var);
		// Create ClientFactory using deferred binding so we can replace with
		// different impls in gwt.xml
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);
		activityManager.setDisplay(appWidget);
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController, eventBus, solicitudesPlace);
		historyHandler.register(placeController, eventBus, loginPlace);
		historyHandler.register(placeController, eventBus,
				asignarSolicitudPlace);
		historyHandler.register(placeController, eventBus, crearSolicitudPlace);
		if(UsuarioSingleton.getInstance().getLogin() != null)
		{
			historyHandler.register(placeController, eventBus, mainPagePlace);
		}
		RootPanel.get().add(appWidget);
		historyHandler.handleCurrentHistory();
	}
}
