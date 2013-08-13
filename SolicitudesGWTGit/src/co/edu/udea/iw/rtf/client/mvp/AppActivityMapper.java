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
package co.edu.udea.iw.rtf.client.mvp;

import co.edu.udea.iw.rtf.client.ClientFactory;
import co.edu.udea.iw.rtf.client.activity.AsignarSolicitudActivity;
import co.edu.udea.iw.rtf.client.activity.CrearSolicitudActivity;
import co.edu.udea.iw.rtf.client.activity.LoginActivity;
import co.edu.udea.iw.rtf.client.activity.MainPageActivity;
import co.edu.udea.iw.rtf.client.activity.SolicitudesActivity;
import co.edu.udea.iw.rtf.client.place.AsignarSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.CrearSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.LoginPlace;
import co.edu.udea.iw.rtf.client.place.MainPagePlace;
import co.edu.udea.iw.rtf.client.place.SolicitudesPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

/**
 * ActivityMapper associates each {@link Place} with its corresponding
 * {@link Activity}.
 */
public class AppActivityMapper implements ActivityMapper {

	/**
	 * Provided for {@link Activitie}s.
	 */
	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	/**
	 * Método usado para crear una actividad a partir de un place ingresado como
	 * argumento.
	 * 
	 * @param place
	 *            El place a partir del cual se desea crear un Activity.
	 * @return La actividad asociada con el Place ingresado como argumento.
	 * */
	@Override
	public Activity getActivity(Place place) {

		if (place instanceof SolicitudesPlace) {
			return new SolicitudesActivity((SolicitudesPlace) place,
					clientFactory);
		}
		if (place instanceof CrearSolicitudPlace) {
			return new CrearSolicitudActivity((CrearSolicitudPlace) place,
					clientFactory);
		}
		if (place instanceof LoginPlace) {
			return new LoginActivity((LoginPlace) place, clientFactory);
		}
		if (place instanceof MainPagePlace) {
			return new MainPageActivity((MainPagePlace) place, clientFactory);
		}
		if (place instanceof AsignarSolicitudPlace) {
			return new AsignarSolicitudActivity((AsignarSolicitudPlace) place,
					clientFactory);
		}
		return null;
	}

}
