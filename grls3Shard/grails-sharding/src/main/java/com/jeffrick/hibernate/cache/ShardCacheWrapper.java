package com.jeffrick.hibernate.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;

import java.util.Map;

/**
 * Created by spandey on 5/18/17.
 */
public class ShardCacheWrapper implements EntityRegion{

    EntityRegion baseEntityRegion;
    ShardCacheWrapper(EntityRegion entityRegion){
        baseEntityRegion = entityRegion;
    }

    @Override
    public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return new ShardEntityRegionAccessStrategy(baseEntityRegion);
    }

    @Override
    public boolean isTransactionAware() {
        return baseEntityRegion.isTransactionAware();
    }

    @Override
    public CacheDataDescription getCacheDataDescription() {
        return baseEntityRegion.getCacheDataDescription();
    }

    @Override
    public String getName() {
        return baseEntityRegion.getName();
    }

    @Override
    public void destroy() throws CacheException {
        baseEntityRegion.destroy();

    }

    @Override
    public boolean contains(Object key) {
        return baseEntityRegion.contains(key);
    }

    @Override
    public long getSizeInMemory() {
        return baseEntityRegion.getSizeInMemory();
    }

    @Override
    public long getElementCountInMemory() {
        return baseEntityRegion.getElementCountInMemory();
    }

    @Override
    public long getElementCountOnDisk() {
        return baseEntityRegion.getElementCountOnDisk();
    }

    @Override
    public Map toMap() {
        return baseEntityRegion.toMap();
    }

    @Override
    public long nextTimestamp() {
        return baseEntityRegion.nextTimestamp();
    }

    @Override
    public int getTimeout() {
        return baseEntityRegion.getTimeout();
    }
}
