package com.test.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.test.constants.SourcePath;

public class PropUtility {

	private Properties propertyFile = null;
	private FileInputStream fis = null;

	public Properties loadFile(String filename) {
		propertyFile = new Properties();
		String propertyFilePath = null;
		
		switch (filename) {
		case "taloginproperties":
			propertyFilePath = SourcePath.TekArch_lOGIN;
			break;
		}
		try {
			fis = new FileInputStream(propertyFilePath);
			try {
				propertyFile.load(fis);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return propertyFile;
	}

	public String getPropertyValue(String Key) {
		String value = propertyFile.getProperty(Key);
		return value;
	}
}
