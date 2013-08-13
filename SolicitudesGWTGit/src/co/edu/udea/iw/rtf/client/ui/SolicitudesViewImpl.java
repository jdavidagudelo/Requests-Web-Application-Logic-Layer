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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link SolicitudesView}.
 */
public class SolicitudesViewImpl extends Composite implements SolicitudesView {

	interface Binder extends UiBinder<Widget, SolicitudesViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;
	@UiField
	FlexTable tabla;
	@UiField
	VerticalPanel verticalPanel;

	public SolicitudesViewImpl() {
		initWidget(binder.createAndBindUi(this));
		tabla.setText(0, 0, "Nombre Completo");
		tabla.setText(0, 1, "Correo electronico");
		tabla.setText(0, 2, "Fecha de solicitud");
		tabla.setText(0, 3, "Tipo de solicitud");
		tabla.setText(0, 4, "Texto de la solicitud");
		tabla.getCellFormatter().setWidth(0, 0, "100px");
		tabla.getCellFormatter().setWidth(0, 1, "100px");
		tabla.getCellFormatter().setWidth(0, 2, "100px");
		tabla.getCellFormatter().setWidth(0, 3, "100px");
		tabla.getCellFormatter().setWidth(0, 4, "100px");
		tabla.getRowFormatter().addStyleName(0, "tablaHeader");
	}

	@Override
	public void setLogin(String login) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.setListener(listener);
	}

	@Override
	public FlexTable getTable() {
		// TODO Auto-generated method stub
		return tabla;
	}

	public void setListener(Presenter listener) {
		this.listener = listener;
	}
}
