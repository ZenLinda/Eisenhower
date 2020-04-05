package com.zenlinda.eisenhower.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.zenlinda.eisenhower.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.zenlinda.eisenhower.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.zenlinda.eisenhower.domain.User.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.Authority.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.User.class.getName() + ".authorities");
            createCache(cm, com.zenlinda.eisenhower.domain.PersistentToken.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.zenlinda.eisenhower.domain.Utilisateur.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.Utilisateur.class.getName() + ".matrices");
            createCache(cm, com.zenlinda.eisenhower.domain.DegreUrgence.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.DegreUrgence.class.getName() + ".taches");
            createCache(cm, com.zenlinda.eisenhower.domain.DegreImportance.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.DegreImportance.class.getName() + ".taches");
            createCache(cm, com.zenlinda.eisenhower.domain.Tache.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.Matrice.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.Matrice.class.getName() + ".taches");
            createCache(cm, com.zenlinda.eisenhower.domain.CategorieTache.class.getName());
            createCache(cm, com.zenlinda.eisenhower.domain.CategorieTache.class.getName() + ".taches");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
