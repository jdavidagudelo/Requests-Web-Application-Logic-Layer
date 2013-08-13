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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link LoginView}.
 */
public class LoginViewImpl extends Composite implements LoginView {

	interface Binder extends UiBinder<Widget, LoginViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;
	@UiField
	Button buttonLogin;

	private TextBox textBoxLogin = new TextBox();
	private PasswordTextBox textBoxClave = new PasswordTextBox();
	private Label labelLogin = new Label("Login");
	private Label labelClave = new Label("Usuario");
	@UiField
	TextBox login;
	@UiField
	PasswordTextBox pass;

	public LoginViewImpl() {
		initWidget(binder.createAndBindUi(this));
		textBoxLogin.setMaxLength(15);
		textBoxClave.setMaxLength(25);
		login.setMaxLength(15);
		pass.setMaxLength(25);
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@UiHandler("buttonLogin")
	void onButtonClick(ClickEvent event) {
		listener.testUsuario();
	}

	@Override
	public TextBox getTextBoxLogin() {
		return login;
	}

	@Override
	public PasswordTextBox getTextBoxClave() {
		// TODO Auto-generated method stub
		return pass;
	}
}
