package com.finaxys.kafka;


import java.io.Closeable;
import java.util.Map;

public interface Serializer<T> extends Closeable {

	void configure(Map<String, ?> var1, boolean var2);

	byte[] serialize(String var1, T var2);

	void close();


}
