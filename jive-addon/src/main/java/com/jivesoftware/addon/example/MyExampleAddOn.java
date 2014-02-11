/*
 *
 *  * Copyright 2013 Jive Software
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.jivesoftware.addon.example;

import com.jivesoftware.addon.example.tile.*;
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
            MyExampleTableTile.class,
            MyExampleCalendarTile.class,
            MyExampleGaugeTile.class,
            MyExampleGalleryTile.class
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