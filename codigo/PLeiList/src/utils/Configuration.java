package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Properties;

public class Configuration {
		
	// 2o passo - instancia static
	private static Configuration INSTANCE = null;
	
	// 3o passo - criar getter static
	
	public static Configuration getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			 INSTANCE = new Configuration();
		}
		return INSTANCE;
	}
	
	private Properties prop = new Properties();
	
	// 1o passo - constructor private
	private Configuration() {
		try {
			prop.load(new FileInputStream("pleilist.properties"));
		} catch (FileNotFoundException e) {
			// Lidar com erros amanhã
		} catch (IOException e) {
			// Lidar com erros amanhã
			e.printStackTrace();
		}
	}

	public int getInt(String chave) {
		return Integer.parseInt(prop.getProperty(chave));
	}
	
	public String getString(String chave) {
		return prop.getProperty(chave);
	}

	public String[] getStringArray(String chave) {
		return getString(chave).split(";");
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T createInstance(String className, T defaultObject) {
		Class<T> c = null;
		try {
			c = (Class<T>) Class.forName(className);
			
		} catch (ClassNotFoundException e1) {
			// Mais erros para amanhã
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		T ins = defaultObject;
		try {
			Constructor<T> cons = c.getConstructor();
			ins = cons.newInstance();
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins;
	}

	@SuppressWarnings("unchecked")
	public <T> Iterable<T> getInstances(String chave, T defaultObject) {
		return (Iterable<T>) Arrays.stream(this.getStringArray(chave)).map((x) -> this.createInstance(x, defaultObject));
	}

	public <T> T createInstanceFromKey(String chave, T defaultObject) {
		String className = this.getString(chave);
		return this.createInstance(className, defaultObject);
	}
	
}
