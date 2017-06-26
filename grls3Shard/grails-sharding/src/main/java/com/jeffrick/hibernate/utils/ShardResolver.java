package com.jeffrick.hibernate.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by spandey on 5/18/17.
 */
public class ShardResolver {
    public static String resolveCurrentShard(){
        return getShardName(currentShardConfig());
    }

    //will return object of ShardConfig
    private static Object currentShardConfig(){
        Class cls;
        Object obj = null;
        try {
            cls = Class.forName("com.jeffrick.grails.plugin.sharding.CurrentShard");
            Method method = cls.getDeclaredMethod("get");
            System.out.println("hello hello");
            obj = (Object) method.invoke(cls);
        }catch (ClassNotFoundException cnfe){
            cnfe.getCause().printStackTrace();
        }catch (NoSuchMethodException nsme){
            nsme.getCause().printStackTrace();
        }catch (InvocationTargetException ite){
            ite.getCause().printStackTrace();
        }catch(IllegalAccessException iae){
            iae.getCause().printStackTrace();
        }
        return obj;
    }

    private static String getShardName(Object shardConfigObject){
        String shardName = "";
        try {
        Method method = shardConfigObject.getClass().getDeclaredMethod("getName");
        shardName = (String) method.invoke(shardConfigObject);
        }catch (NoSuchMethodException nsme){
            nsme.getCause().printStackTrace();
        }catch (InvocationTargetException ite){
            ite.getCause().printStackTrace();
        }catch(IllegalAccessException iae){
            iae.getCause().printStackTrace();
        }
        return shardName;
    }

    /*private static Object indexDatasourceName(){
        Class cls;
        Object obj = null;
        try {
            cls = Class.forName("com.jeffrick.grails.plugin.sharding.CurrentShard");
            Method method = cls.getDeclaredMethod("get");
            System.out.println("hello hello");
            obj = (Object) method.invoke(cls);
        }catch (ClassNotFoundException cnfe){
            cnfe.getCause().printStackTrace();
        }catch (NoSuchMethodException nsme){
            nsme.getCause().printStackTrace();
        }catch (InvocationTargetException ite){
            ite.getCause().printStackTrace();
        }catch(IllegalAccessException iae){
            iae.getCause().printStackTrace();
        }
        return obj;
    }*/


}
