package co.bassan.lpaecomms.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(co.bassan.lpaecomms.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.Stock.class.getName(), jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.Stock.class.getName() + ".stockIDS", jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.Clients.class.getName(), jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.Clients.class.getName() + ".clientIDS", jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.Invoices.class.getName(), jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.Invoices.class.getName() + ".invNos", jcacheConfiguration);
            cm.createCache(co.bassan.lpaecomms.domain.InvoiceItems.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
