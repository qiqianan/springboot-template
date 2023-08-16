package com.west2.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author 1
 * FastJsonRedisSerializer
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    /**
     * 默认编码 UTF-8
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 需要序列化的类
     */
    private Class<T> clazz;

    /**
     * 静态代码块，设置全局自动类型支持
     */
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    /**
     * 构造方法
     * @param clazz
     */
    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 序列化
     * @param t
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
        // JSON.toJSONString(t, SerializerFeature.WriteClassName):
        // 这个方法将对象t转换为带有类名的JSON格式的字符串。
        // SerializerFeature.WriteClassName是Fastjson提供的一个选项，用于在JSON中写入类名，
        // 以便在反序列化时能够正确地还原对象类型。
        return JSON.toJSONString(t, SerializerFeature.WriteClassName ).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        // JSON.parseObject(bytes, clazz):
        // 这个方法将字节数组bytes反序列化为对象。
        // JSON.parseObject(bytes, clazz)方法的第二个参数clazz是反序列化的目标类型，
        // 但是这个方法不会自动将类名写入JSON字符串，所以需要在序列化时手动添加类名。
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }

    /**
     * 获取JavaType
     * @param clazz
     * @return
     */
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
