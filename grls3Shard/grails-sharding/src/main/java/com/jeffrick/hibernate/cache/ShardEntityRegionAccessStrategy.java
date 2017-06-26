package com.jeffrick.hibernate.cache;

import com.jeffrick.hibernate.utils.ShardResolver;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.entity.EntityPersister;

/**
 * Created by spandey on 5/18/17.
 */
public class ShardEntityRegionAccessStrategy implements EntityRegionAccessStrategy {

    EntityRegion baseEntityRegion;
    EntityRegionAccessStrategy baseEntityRegionAccessStrategy;
    AccessType accessType = AccessType.READ_WRITE;
    ShardEntityRegionAccessStrategy(EntityRegion entityRegion){
        baseEntityRegion = entityRegion;
        baseEntityRegionAccessStrategy = baseEntityRegion.buildAccessStrategy(accessType);
    }
    @Override
    public Object generateCacheKey(Object id, EntityPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
        System.out.println("======>>>>>generateCacheKey");
        System.out.println("the id=====>>>>>"+getKey(id));
        return baseEntityRegionAccessStrategy.generateCacheKey(getKey(id),persister,factory,tenantIdentifier);
    }

    @Override
    public Object getCacheKeyId(Object cacheKey) {
        System.out.println("======>>>>>getCacheKeyId");
        return baseEntityRegionAccessStrategy.getCacheKeyId(cacheKey);
    }

    @Override
    public EntityRegion getRegion() {
        System.out.println("======>>>>>EntityRegion");
        return baseEntityRegionAccessStrategy.getRegion();
    }

    @Override
    public boolean insert(SessionImplementor session, Object key, Object value, Object version) throws CacheException {
        System.out.println("======>>>>>insert");
        return baseEntityRegionAccessStrategy.insert(session,getKey(key),value,version);
    }

    @Override
    public boolean afterInsert(SessionImplementor session, Object key, Object value, Object version) throws CacheException {
        System.out.println("======>>>>>afterInsert");
        return baseEntityRegionAccessStrategy.afterInsert(session,getKey(key),value,version);
    }

    @Override
    public boolean update(SessionImplementor session, Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        System.out.println("======>>>>>update");
        return baseEntityRegionAccessStrategy.update(session,getKey(key),value,currentVersion,previousVersion);
    }

    @Override
    public boolean afterUpdate(SessionImplementor session, Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        System.out.println("======>>>>>afterUpdate");
        return baseEntityRegionAccessStrategy.afterUpdate(session,getKey(key),value,currentVersion,previousVersion,lock);
    }

    @Override
    public Object get(SessionImplementor session, Object key, long txTimestamp) throws CacheException {
        System.out.println("======>>>>>get");
        System.out.println("za key ==============>>>>>"+key);
        return baseEntityRegionAccessStrategy.get(session,getKey(key),txTimestamp);
    }

    @Override
    public boolean putFromLoad(SessionImplementor session, Object key, Object value, long txTimestamp, Object version) throws CacheException {
        System.out.println("======>>>>>putFromLoad");
        return baseEntityRegionAccessStrategy.putFromLoad(session,getKey(key),value,txTimestamp,version);
    }

    @Override
    public boolean putFromLoad(SessionImplementor session, Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        System.out.println("======>>>>>putFromLoad2");
        System.out.println("=====================>>>>>>>>key "+key);
        System.out.println("==================>>>>>value"+value);
        return baseEntityRegionAccessStrategy.putFromLoad(session,getKey(key),value,txTimestamp,version,minimalPutOverride);
    }

    @Override
    public SoftLock lockItem(SessionImplementor session, Object key, Object version) throws CacheException {
        System.out.println("======>>>>>lockItem");
        return baseEntityRegionAccessStrategy.lockItem(session,getKey(key),version);
    }

    @Override
    public SoftLock lockRegion() throws CacheException {
        System.out.println("======>>>>>lockRegion");
        return baseEntityRegionAccessStrategy.lockRegion();
    }

    @Override
    public void unlockItem(SessionImplementor session, Object key, SoftLock lock) throws CacheException {
        System.out.println("======>>>>>unlockItem");
        baseEntityRegionAccessStrategy.unlockItem(session,getKey(key),lock);
    }

    @Override
    public void unlockRegion(SoftLock lock) throws CacheException {
        System.out.println("======>>>>>unlockRegion");
        baseEntityRegionAccessStrategy.unlockRegion(lock);
    }

    @Override
    public void remove(SessionImplementor session, Object key) throws CacheException {
        System.out.println("======>>>>>remove");
        baseEntityRegionAccessStrategy.remove(session,getKey(key));
    }

    @Override
    public void removeAll() throws CacheException {
        System.out.println("======>>>>>removeAll");
        baseEntityRegionAccessStrategy.removeAll();
    }

    @Override
    public void evict(Object key) throws CacheException {
        System.out.println("======>>>>>evict");
        baseEntityRegionAccessStrategy.evict(getKey(key));
    }

    @Override
    public void evictAll() throws CacheException {
        System.out.println("======>>>>>evictAll(");
        baseEntityRegionAccessStrategy.evictAll();
    }

    protected String getPrefix() {
        /*if(getRegionName().equals(CurrentShard.getIndexDataSourceName())) {
            return("INDEX");
        } else {
            ShardConfig current = CurrentShard.get();
            return(current.getName());
        }*/
        System.out.println("\n\n\n\n");
        System.out.println(baseEntityRegion.getName());
        return ShardResolver.resolveCurrentShard();
    }

    protected Object getKey(Object baseKey) {
        return (getPrefix() + baseKey);
    }
}
