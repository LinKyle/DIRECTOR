package com.kingdee.eas.bos.pureflex.manager.sce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonFileReader {
	private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$
	/**
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	public static String readFile(String filePath) throws IOException {
		StringBuilder string = new StringBuilder();
		File res = new File(filePath);
		InputStream fis = new FileInputStream(res) ;
		BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		try {
			while((line = buffer.readLine()) != null) {
				string.append(line + LINE_SEPARATOR);
			}
		} catch (IOException e) {
			throw e;
		}
		buffer.close();
		return string.toString();
	}
	
	public static void main(String[] args) {
		String updateModel = null;
		try {
			updateModel = JsonFileReader.readFile( "E:/EasDirector/config/updateDeployment.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String updateDate = updateModel.replace("[WORKLOAD_NAME]", "EAS300").replace("[IP]", "192.168.1.123").replace("[GATEWAY]", "192.168.1.1"); 
		System.out.println(updateDate);
	}
}
