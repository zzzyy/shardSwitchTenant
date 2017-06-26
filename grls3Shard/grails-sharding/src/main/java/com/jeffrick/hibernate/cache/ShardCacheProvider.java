package com.jeffrick.hibernate.cache;

//import com.jeffrick.grails.plugin.sharding.CurrentShard;
import com.jeffrick.hibernate.utils.ShardResolver;
import groovy.lang.GroovyClassLoader;
import net.sf.ehcache.Ehcache;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.hibernate.cache.ehcache.internal.regions.EhcacheEntityRegion;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by spandey on 5/18/17.
 */
public class ShardCacheProvider extends SingletonEhCacheRegionFactory {

    //Logger log = LoggerFactory.logger(this.getClass());

    @Override
    public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata)

            throws CacheException{

        System.out.println("\n\n\n\n\n");
        //GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class cls;
        String dsourceName = ShardResolver.resolveCurrentShard();
        regionName = dsourceName+"."+regionName;

        System.out.println(regionName);
        System.out.println(properties.stringPropertyNames());
        System.out.println(metadata.toString());
        EntityRegion entityRegion =  super.buildEntityRegion(regionName,properties,metadata);
        return new ShardCacheWrapper(entityRegion);
        //return new EhcacheEntityRegion( accessStrategyFactory, getCache( regionName ), settings, metadata, properties );

        //entityRegion.buildAccessStrategy()

    }


}
