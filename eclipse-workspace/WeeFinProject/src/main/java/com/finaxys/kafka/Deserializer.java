package com.finaxys.kafka;

import java.io.Closeable;
import java.util.Map;

public interface Deserializer<T> extends Closeable {

	void configure(Map<String, ?> var1, boolean var2);

	T deserialize(String var1, byte[] var2);

	void close();

}
