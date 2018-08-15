package com.yousong.yousong.model.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yousong.yousong.model.local.Ads;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;

/**
 * 自定义的集合类型Gson序列化适配器工厂，用于解析特定类型的集合
 *
 * @author 超悟空
 * @version 1.0 2018/8/15
 * @since 1.0
 */
public class MyCollectionTypeAdapterFactory implements TypeAdapterFactory {

    /**
     * 注册的类型
     */
    private Class<?>[] REGISTER_TYPES = new Class<?>[]{Ads.class};

    /**
     * 对象构造器
     */
    private ConstructorConstructor constructorConstructor = new ConstructorConstructor(new
            HashMap<>());

    @Override
    public <T> TypeAdapter<T> create(Gson gson , TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }

        ParameterizedType parameterizedType = (ParameterizedType) typeToken.getType();

        if (parameterizedType.getActualTypeArguments().length > 0) {
            TypeToken<?> elementType = TypeToken.get(parameterizedType.getActualTypeArguments()[0]);

            for (Class<?> type : REGISTER_TYPES) {
                if (type.isAssignableFrom(elementType.getRawType())) {
                    TypeAdapter<?> elementTypeAdapter = gson.getAdapter(elementType.getRawType());
                    ObjectConstructor<T> constructor = constructorConstructor.get(typeToken);

                    @SuppressWarnings({"unchecked" , "rawtypes"})
                    // create() doesn't define a type parameter
                            TypeAdapter<T> result = new Adapter(elementTypeAdapter , constructor);
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * 解析集合的适配器
     */
    private static final class Adapter<E> extends TypeAdapter<Collection<E>> {
        private final TypeAdapter<E> elementTypeAdapter;
        private final ObjectConstructor<? extends Collection<E>> constructor;

        private Adapter(TypeAdapter<E> elementTypeAdapter , ObjectConstructor<? extends
                Collection<E>> constructor) {
            this.elementTypeAdapter = elementTypeAdapter;
            this.constructor = constructor;
        }

        @Override
        public Collection<E> read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            Collection<E> collection = constructor.construct();
            in.beginArray();
            while (in.hasNext()) {
                E instance = elementTypeAdapter.read(in);
                collection.add(instance);
            }
            in.endArray();
            return collection;
        }

        @Override
        public void write(JsonWriter out , Collection<E> collection) throws IOException {
            if (collection == null) {
                out.nullValue();
                return;
            }

            out.beginArray();
            for (E element : collection) {
                elementTypeAdapter.write(out , element);
            }
            out.endArray();
        }
    }
}
