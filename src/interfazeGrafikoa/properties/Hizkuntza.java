package interfazeGrafikoa.properties;

import java.io.IOException;
import java.util.Properties;

public class Hizkuntza extends Properties {
	private static final long serialVersionUID = 1L;

	public Hizkuntza(String hizkuntza) {
		getProperties(hizkuntza+".properties");
	}

	public String getIzena(String pJokalari){
		return (pJokalari.split("\\.")[0]+"."+ getProperty(pJokalari.split("\\.")[1]));
	}
	
	private void getProperties(String hizkuntza) {
		try {
			Properties properties = new Properties();
			Thread currentThread = Thread.currentThread();
			ClassLoader contextClassLoader = currentThread.getContextClassLoader();
			java.io.InputStream propertiesStream = getClass().getResourceAsStream(hizkuntza);
			if (propertiesStream != null) {
			  this.load(propertiesStream);
			} else {
				
			}
		} catch (IOException ex) {

		}
	}
}