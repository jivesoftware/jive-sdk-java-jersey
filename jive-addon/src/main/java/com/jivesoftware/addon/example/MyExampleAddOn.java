package com.jivesoftware.addon.example;

import com.jivesoftware.addon.example.tile.MyExampleListTile;
import com.jivesoftware.addon.example.tile.MyExampleTableTile;
import com.jivesoftware.sdk.JiveAddOnApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@Singleton
@ApplicationPath("example")
public class MyExampleAddOn extends JiveAddOnApplication {
    private static final Logger log = LoggerFactory.getLogger(MyExampleAddOn.class);

    public MyExampleAddOn() {
        /*** THIS MUST BE FIRST ****/
        super();

        //*** NEED TO REGISTER ANY CLASSES THAT YOU WILL USE CDI INJECTIONS
        //*** THIS INCLUDES FOR @Inject, Events, @Context, @Observes ...
        registerInjectables(
                MyExampleListTile.class,
                MyExampleTableTile.class
        );

    } // end constructor

//    @Override
//    public TileInstanceProvider getTileInstanceProvider() {
//        return super.getTileInstanceProvider();
//    } // end getTileInstanceProvider
//
//    @Override
//    public AddOnConfigProvider getAddOnConfigProvider() {
//        return super.getAddOnConfigProvider();
//    } // end getAddOnConfigProvider
//
//    @Override
//    public HealthStatusProvider getHealthStatusProvider() {
//        return super.getHealthStatusProvider();
//    } // end getHealthStatusProvider
//
//    @Override
//    public JiveInstanceProvider getJiveInstanceProvider() {
//        return super.getJiveInstanceProvider();
//    } // end getJiveInstanceProvider
//
//    @Override
//    public StorageInstanceProvider getStorageInstanceProvider() {
//        return super.getStorageInstanceProvider();
//    } // end getStorageInstanceProvider

} // end class