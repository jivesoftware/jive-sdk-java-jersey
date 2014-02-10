package com.jivesoftware.sdk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jivesoftware.sdk.api.entity.*;
import com.jivesoftware.sdk.api.entity.impl.memory.*;
import com.jivesoftware.sdk.client.JiveAPIClient;
import com.jivesoftware.sdk.client.JiveTileClient;
import com.jivesoftware.sdk.client.oauth.JiveOAuthClient;
import com.jivesoftware.sdk.client.oauth.JiveOAuthEventListener;
import com.jivesoftware.sdk.service.filter.*;
import com.jivesoftware.sdk.service.health.HealthService;
import com.jivesoftware.sdk.service.instance.InstanceService;
import com.jivesoftware.sdk.service.oauth.JiveOAuth2Service;
import com.jivesoftware.sdk.service.oauth.facebook.FacebookOAuth2Service;
import com.jivesoftware.sdk.service.oauth.google.GoogleOAuth2Service;
import com.jivesoftware.sdk.service.oauth.twitter.TwitterOAuth1Service;
import com.jivesoftware.sdk.service.storage.StorageService;
import com.jivesoftware.sdk.service.tile.TileService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.weld.Weld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Singleton;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by rrutan on 1/29/14.
 */
@Singleton
public abstract class JiveAddOnApplication extends ResourceConfig {
    private static final Logger log = LoggerFactory.getLogger(JiveAddOnApplication.class);

    public JiveAddOnApplication() {

        List<Class> addOnBeans = getAddOnBeans();

        //*** NEEDED FOR THE EVENT QUEUE ***
        register(new CDIBinder(addOnBeans));

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                /*** BIND PROVIDER FACTORIES ***/
                bindFactory(AddOnConfigProviderFactory.class).to(AddOnConfigProvider.class);
                bindFactory(HealthStatusProviderFactory.class).to(HealthStatusProvider.class);
                bindFactory(JiveInstanceProviderFactory.class).to(JiveInstanceProvider.class);
                bindFactory(StorageInstanceProviderFactory.class).to(StorageInstanceProvider.class);
                bindFactory(TileInstanceProviderFactory.class).to(TileInstanceProvider.class);
            }
        });

        register(JacksonFeature.class);

        packages("com.jivesoftware.sdk.service.filter",
                 "com.jivesoftware.sdk.service.health",
                 "com.jivesoftware.sdk.service.instance",
                 "com.jivesoftware.sdk.service.oauth",
                 "com.jivesoftware.sdk.service.oauth",
                 "com.jivesoftware.sdk.service.storage",
                 "com.jivesoftware.sdk.service.tile");

        packages(true,"com.jivesoftware.sdk.api",
                      "com.jivesoftware.sdk.client",
                      "com.jivesoftware.sdk.config",
                      "com.jivesoftware.sdk.event");

      } // end constructor

    private List<Class> getAddOnBeans() {
        if (log.isTraceEnabled()) { log.trace("Collecting CDI Binder Classes ..."); }

        List<Class> beanClasses = Lists.newArrayList();

        //TODO: REPLACE WITH ANNOTATION SCAN?

        /** ALLOWS FOR THE INJECTION OF PROVIDERS THROUGHOUT THE APP **/
        beanClasses.add(AddOnConfigProviderFactory.class);
        beanClasses.add(HealthStatusProviderFactory.class);
        beanClasses.add(JiveInstanceProviderFactory.class);
        beanClasses.add(StorageInstanceProviderFactory.class);
        beanClasses.add(TileInstanceProviderFactory.class);

        /** MISC CLASSES WITH INJECTION NEEDS **/
        beanClasses.add(JiveOAuthEventListener.class);
        beanClasses.add(JiveOAuthClient.class);
        beanClasses.add(JiveTileClient.class);
        beanClasses.add(JiveAPIClient.class);

        /** ALLOWS FOR EVENTS INJECTION INTO ALL SERVICES **/
        beanClasses.add(InstanceService.class);
        beanClasses.add(TileService.class);
        beanClasses.add(JiveOAuth2Service.class);
        beanClasses.add(StorageService.class);
        beanClasses.add(HealthService.class);

        /** ALLOWS FOR THE INJECTION OF PROVIDERS/OBJECTS INTO FILTERS **/
        beanClasses.add(JiveSignedFetchValidationRequestFilter.class);
        beanClasses.add(JiveSignedFetchValidator.class);
        beanClasses.add(JiveAuthorizationValidationRequestFilter.class);
        beanClasses.add(JiveAuthorizationValidator.class);
        beanClasses.add(JiveSignatureValidationRequestFilter.class);
        beanClasses.add(JiveSignatureValidator.class);

        /*** GOING AHEAD AND MARKING THESE FOR INCLUSION, IF NOT ACIVATED, THEY WILL NOT LOAD ***/
        beanClasses.add(FacebookOAuth2Service.class);
        beanClasses.add(GoogleOAuth2Service.class);
        beanClasses.add(TwitterOAuth1Service.class);

        return beanClasses;

    } // end getAddOnBeans

    //TODO: TURN BACK ON WHEN WE FIGURE OUT IDENTITY SIGNING FROM APPS
    private void activateGoogleOAuthService() {
        packages(true,"com.jivesoftware.sdk.service.oauth."+GoogleOAuth2Service.SERVICE_NAME);
    } // end activateGoogleOAuthService

    //TODO: TURN BACK ON WHEN WE FIGURE OUT IDENTITY SIGNING FROM APPS
    private void activateFacebookOAuthService() {
        packages(true,"com.jivesoftware.sdk.service.oauth."+FacebookOAuth2Service.SERVICE_NAME);
    } // end activateFacebookOAuthService

    //TODO: TURN BACK ON WHEN WE FIGURE OUT IDENTITY SIGNING FROM APPS
    private void activateTwitterOAuthService() {
        packages(true,"com.jivesoftware.sdk.service.oauth."+TwitterOAuth1Service.SERVICE_NAME);
    } // end activateTwitterOAuthService

    //NOTE:  CUSTOMERS CAN OVERRIDE ANY IMPLEMENTATION WITH THEIR OWN IMPLEMENTATIONS
    public AddOnConfigProvider getAddOnConfigProvider() {
        if (log.isDebugEnabled()) { log.debug("getAddOnConfigProvider called.."); }
        return MemoryAddOnConfigProvider.getInstance();
    } // end getAddOnConfigProvider

    public HealthStatusProvider getHealthStatusProvider() {
        if (log.isDebugEnabled()) { log.debug("getHealthStatusProvider called.."); }
        return MemoryHealthStatusProvider.getInstance();
    } // end getHealthStatusProvider

    public JiveInstanceProvider getJiveInstanceProvider() {
        if (log.isDebugEnabled()) { log.debug("getJiveInstanceProvider called.."); }
        return MemoryJiveInstanceProvider.getInstance();
    } // end getJiveInstanceProvider

    public StorageInstanceProvider getStorageInstanceProvider() {
        if (log.isDebugEnabled()) { log.debug("getStorageInstanceProvider called.."); }
        return MemoryStorageInstanceProvider.getInstance();
    }
    public TileInstanceProvider getTileInstanceProvider() {
        if (log.isDebugEnabled()) { log.debug("getTileInstanceProvider called.."); }
        return MemoryTileInstanceProvider.getInstance();
    } // end getTileInstanceProvider

    public void registerInjectables(Class... injectables) {
        if (injectables == null) {
            return;
        } // end if

        Set<String> packages = Sets.newHashSet();

        //DE-DUPS PACKAGES
        for (Class tileClass : injectables) {
            packages.add(tileClass.getPackage().getName());
        } // end for tileClass

        for (String packageName : packages) {
            if (log.isDebugEnabled()) { log.debug("Registering Tile Package ["+packageName+"]"); }
            packages(packageName);
        } // end for packageName

        register(new AddOnCDIBinder(Lists.newArrayList(injectables)));
    } // end registerTiles

    class AddOnCDIBinder extends CDIBinder {
        public AddOnCDIBinder() {}
        public AddOnCDIBinder(List<Class> beanClasses) {
          setBeanClasses(beanClasses);
        } // end constructor
    }

    class CDIBinder extends AbstractBinder {

      private List<Class> beanClasses;

        public CDIBinder() {}
        public CDIBinder(List<Class> beanClasses) {
          this.beanClasses = beanClasses;
        } // end constructor

        public void setBeanClasses(List<Class> beanClasses) {
            this.beanClasses = beanClasses;
        }

        public List<Class> getBeanClasses() {
        return beanClasses;
        }

        @Override
        protected void configure() {
            BeanManager bm = getBeanManager();
            try {
                for (Class clazz : beanClasses) {
                    bind(getBean(bm,clazz)).to(clazz);
                } // end for clazz
            } catch (NoSuchElementException nsee) {
                if (log.isTraceEnabled()) { log.trace("No Idea Why This Happens - But Catch and Ignore Work Fine", nsee); }
                /*** NOOP: O IDEA WHY THIS IS FIRING, BUT EVERYTHING PROCESSES FINE SO CATCHING IT FOR NOW ***/
            } // end try/catch
        } // end configure

        private BeanManager getBeanManager() {
            // is there a better way to get the bean manager?
            return new Weld().getBeanManager();
        }

        private <T> T getBean(BeanManager bm, Class<T> clazz) {
            Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
            CreationalContext<T> ctx = bm.createCreationalContext(bean);
            return (T) bm.getReference(bean, clazz, ctx);
        }
    } // end CDIBinder

} // end class
