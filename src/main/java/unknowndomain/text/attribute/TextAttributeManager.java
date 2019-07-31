package unknowndomain.text.attribute;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

public class TextAttributeManager {

    private BiMap<String,Class<? extends TextAttribute>> attributeHashMap = HashBiMap.create();

    private HashMap<String, Function<String,TextAttribute>> deserializeMap = new HashMap<>();

    public void putAttribute(String name,Class<? extends TextAttribute> clazz){
        attributeHashMap.put(name,clazz);

        Function<String,TextAttribute> deserializeFunction = null;

        for(Method method : clazz.getMethods()){

            if(Modifier.isStatic(method.getModifiers())&&method.getName().equals("deserialize")&&method.getParameterCount()==1&&method.getParameterTypes()[0].equals(String.class)&&TextAttribute.class.isAssignableFrom(method.getReturnType())){
                deserializeFunction = (text -> {
                    try {
                        return (TextAttribute) method.invoke(null,text);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("attribute deserialize failed");
                });
            }
        }

        if(deserializeFunction==null){
            for(Constructor constructor : clazz.getConstructors()){

                if(constructor.getParameterCount()==1&&constructor.getParameterTypes()[0].equals(String.class)){
                    deserializeFunction = (text -> {
                        try {
                            return (TextAttribute) constructor.newInstance(text);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        throw new RuntimeException("attribute deserialize failed");
                    });
                }

            }
        }

        if(deserializeFunction == null){
            throw new RuntimeException("TextAttribute: "+clazz.toString()+" must have a deserialize method");
        }

        deserializeMap.put(name,deserializeFunction);
    }

    public String getName(Class<? extends TextAttribute> clazz){
        return attributeHashMap.inverse().get(clazz);
    }

    public TextAttribute getAttribute(String name,Object... objects){
        Class<? extends TextAttribute> clazz = attributeHashMap.get(name);
        if(clazz==null)
            throw new NullPointerException("TextAttribute ["+name+"]"+" does not exist");
        Constructor[] constructors = clazz.getConstructors();

        TextAttribute instance = null;

        for(Constructor<? extends TextAttribute> constructor : constructors){
            if(instance!=null)
                break;
            if(constructor.getParameterTypes().length!=objects.length)
                continue;
            try {
                instance = constructor.newInstance(objects);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if(instance==null)
            throw new IllegalArgumentException("TextAttribute:"+name+"  argument:"+ Arrays.toString(objects));

        return instance;
    }

    public TextAttribute deserialize(String name,String serializeText){
        return deserializeMap.get(name).apply(serializeText);
    }

}
