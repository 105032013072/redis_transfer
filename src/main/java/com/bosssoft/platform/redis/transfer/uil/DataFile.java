package com.bosssoft.platform.redis.transfer.uil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class DataFile {
	public static String readMaxId(String dataFile, String maxid) throws IOException {
		String id = read(dataFile, maxid);
		if (id == null)
			return "0";
		else
			return id;

	}
	
	public static String read(String dataFile, String key) throws IOException {
		Properties props = getProperty(dataFile);
		String value = props.getProperty(key);
		return value;

	}
	
	protected static Properties getProperty(String dataFile) throws IOException {
		Properties props = new Properties();

		File file = new File(dataFile);
		if(!file.exists())
			file.createNewFile();
		
		InputStream is;
		try {
			is = new BufferedInputStream(new FileInputStream(dataFile));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
		props.load(is);
		is.close();
		return props;
	}

	protected static Properties getProperty(InputStream is) throws IOException {
		Properties props = new Properties();

		props.load(is);
		is.close();
		return props;
	}

	public static void write(String dataFile, String key, String value) throws IOException {
		Properties props = getProperty(dataFile);
		OutputStream fos = new FileOutputStream(dataFile);
		props.setProperty(key, value);

		props.store(fos, "Update '" + key + "' value");
		fos.close();

	}
	
	public static void delete(String dataFile, String key) throws IOException {
		Properties props = getProperty(dataFile);
		OutputStream fos = new FileOutputStream(dataFile);
		props.remove(key);
		
		props.store(fos, "Delete '" + key + "' value");

	}
}
