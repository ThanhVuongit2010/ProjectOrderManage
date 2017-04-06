package vn.app.android.ordermanager.observer;

import java.util.Observable;

/**
 * HTML JavaScript Interface
 * @author thaonp
 *
 */
public class HtmlJSInterface extends Observable {
	//----------------------------------------------------------------------------------------------------------------
	private String mHtml;
	//----------------------------------------------------------------------------------------------------------------
	/**
	 * @return The most recent HTML received by the interface
	 */
	public String getHtml() {
		return this.mHtml;
	}
	//----------------------------------------------------------------------------------------------------------------
	/**
	 * Sets most recent HTML and notifies observers.
	 * @param html The full HTML of a page
	 */
	public void setHtml(String html) {
		this.mHtml = html;
		setChanged();
		notifyObservers(html);
	}
	//----------------------------------------------------------------------------------------------------------------
}