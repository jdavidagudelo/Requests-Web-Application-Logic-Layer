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

import co.edu.udea.iw.rtf.client.place.AsignarSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.CrearSolicitudPlace;
import co.edu.udea.iw.rtf.client.place.LoginPlace;
import co.edu.udea.iw.rtf.client.place.MainPagePlace;
import co.edu.udea.iw.rtf.client.place.SolicitudesPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.PlaceHistoryMapperWithFactory;
import com.google.gwt.place.shared.WithTokenizers;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending {@link PlaceHistoryMapperWithFactory} and creating
 * a separate TokenizerFactory.
 */
@WithTokenizers({ SolicitudesPlace.Tokenizer.class,
		CrearSolicitudPlace.Tokenizer.class, LoginPlace.Tokenizer.class,
		MainPagePlace.Tokenizer.class, AsignarSolicitudPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
