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
package co.edu.udea.iw.rtf.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * A place object representing a particular state of the UI. A Place can be
 * converted to and from a URL history token by defining a
 * {@link PlaceTokenizer} for each {@link Place}, and the
 * {@link PlaceHistoryHandler} automatically updates the browser URL
 * corresponding to each {@link Place} in your app.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 */
public class SolicitudesPlace extends Place {

	private String login;

	public SolicitudesPlace(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<SolicitudesPlace> {

		@Override
		public String getToken(SolicitudesPlace place) {
			return place.getLogin();
		}

		@Override
		public SolicitudesPlace getPlace(String login) {
			return new SolicitudesPlace(login);
		}

	}
}
