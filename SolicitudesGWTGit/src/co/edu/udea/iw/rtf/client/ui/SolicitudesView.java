package co.edu.udea.iw.rtf.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * View base interface. Extends IsWidget so a view impl can easily provide its
 * container widget.
 */
public interface SolicitudesView extends IsWidget {

	void setLogin(String helloName);

	void setPresenter(Presenter listener);

	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);
	}

	FlexTable getTable();
}
