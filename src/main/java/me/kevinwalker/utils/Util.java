package me.kevinwalker.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import me.kevinwalker.main.Main;

public class Util {

	public static void saveResource(String path, File target) {
		try {
			URL url = new URL("jar:file:/"+Main.getBaseDir().getAbsolutePath()+"!/"+path);
			InputStream in = url.openStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			OutputStream out = new FileOutputStream(target);
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
