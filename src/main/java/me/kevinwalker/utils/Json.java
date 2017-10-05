package me.kevinwalker.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Fuck Kevin
 *
 * @author IzzelAliz
 */
public class Json {
	Json parent;
	private JSONObject obj;

	public Json(String json) {
		obj = new JSONObject(new JSONTokener(json));
	}

	public Json(File json) {
		obj = new JSONObject(new JSONTokener(Files.toString(json, "utf-8")));
	}

	public Json(InputStream in) {
		obj = new JSONObject(new JSONTokener(Files.toString(in, "utf-8")));
	}

	public Json(File json, String charset) {
		obj = new JSONObject(new JSONTokener(Files.toString(json, charset)));
	}

	public Json(InputStream in, String charset) {
		obj = new JSONObject(new JSONTokener(Files.toString(in, charset)));
	}

	Json(JSONObject obj) {
		this.obj = obj;
	}

	public String toString() {
		return obj.toString();
	}

	public Json set(String key, Object value) {
		obj.put(key, value);
		return this;
	}

	public HashMap<String, Object> asMap() {
		HashMap<String, Object> map = new HashMap<>();
		obj.keySet().stream().map(s -> map.put(s, obj.get(s)));
		return map;
	}

	public Set<String> keySet() {
		return obj.keySet();
	}

	public Object getObject(String key) {
		return obj.get(key);
	}

	public <E extends Enum<E>> E getEnum(String key, Class<E> clazz) {
		return obj.getEnum(clazz, key);
	}

	public BigDecimal getBigDecimal(String key) {
		return obj.getBigDecimal(key);
	}

	public BigInteger getBigInteger(String key) {
		return obj.getBigInteger(key);
	}

	public long getShort(String key) {
		return obj.getLong(key);
	}

	public boolean getBoolean(String key) {
		return obj.getBoolean(key);
	}

	public String getString(String key) {
		return obj.getString(key);
	}

	public double getDouble(String key) {
		return obj.getDouble(key);
	}

	public int getInt(String key) {
		return obj.getInt(key);
	}

	public <T> JsonArray<T> getArray(String key, Class<T> clazz) {
		return new JsonArray<T>(obj.getJSONArray(key), clazz).setParent(this);
	}

	public Json getParent() {
		return this.parent;
	}

	public Json getJson(String key) {
		return new Json(obj.getJSONObject(key)).setParent(this);
	}

	private Json setParent(Json j) {
		this.parent = j;
		return this;
	}

	public class JsonArray<T> implements Iterable<T> {
		JSONArray arr;
		Class<? extends T> clazz;
		List<T> list;
		Json j;

		@SuppressWarnings("unchecked")
		private JsonArray(JSONArray arr, Class<? extends T> clazz) {
			this.arr = arr;
			this.clazz = clazz;
			list = new ArrayList<>();
			arr.forEach(o -> list.add((T) o));
		}

		public Json getParent() {
			return j;
		}

		private JsonArray<T> setParent(Json j) {
			this.j = j;
			return this;
		}

		@Override
		public Iterator<T> iterator() {
			return list.iterator();
		}

		public Stream<T> stream() {
			return list.stream();
		}

		public Stream<T> parallelStream() {
			return list.parallelStream();
		}

		public void forEach(Consumer<? super T> action) {
			list.forEach(action);
		}

	}

	public static class Files {
		public static String toString(InputStream in, String charset) {
			ByteBuffer bb = ByteBuffer.allocate(1024);
			int length = -1;
			byte[] buffer = new byte[1024];
			try {
				while ((length = in.read(buffer)) != -1)
					bb.put(buffer);
				return new String(bb.array(), charset);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static String toString(File file, String charset) {
			try {
				if (!file.exists())
					return null;
				RandomAccessFile rf = new RandomAccessFile(file, "r");
				rf.seek(0);
				long length = rf.length();
				byte[] content = new byte[(int) length];
				rf.readFully(content);
				rf.close();
				return new String(content, charset);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
