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

package com.jivesoftware.sdk.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by rrutan on 1/30/14.
 */
public class JiveSDKUtils {
    private static final Logger log = LoggerFactory.getLogger(JiveSDKUtils.class);

    @Nonnull
    public static String encodeUrl(@Nonnull String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException uee) {
            //TODO: LOGGER
            System.err.println("Failed encoding URL using UTF-8 charset" + uee.getMessage());
            //noinspection deprecation
            return URLEncoder.encode(url);
        }
    }

    public static String decodeUrl(@Nonnull String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException uee) {
            //TODO: LOGGER
            System.err.println("Failed decoding URL using UTF-8 charset" + uee.getMessage());
            //noinspection deprecation
            return URLDecoder.decode(url);
        }
    }

    public static void initBeanFromProperties(String fileName, Object bean) {

        if (bean == null) {
            if (log.isWarnEnabled()) { log.warn("Bean is null, cant init.  Ignoring ..."); }
            return;
        } // end if

        Properties props = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        try {
            props.load(is);
            for (String key : props.stringPropertyNames()) {
                try {
                    BeanUtils.setProperty(bean, key, props.getProperty(key));
                    if (log.isTraceEnabled()) { log.trace("Sucessfully set ["+key+"] to ["+props.getProperty(key)); }
                } catch(IllegalAccessException iae) {
                    log.error("Unable to Set Field["+key+"] on ["+bean.getClass().getSimpleName()+"], continuing on but may be problematic.  Check your ["+fileName+"]",iae);
                } catch (InvocationTargetException ite) {
                    log.error("Unable to Set Field["+key+"] on ["+bean.getClass().getSimpleName()+"], continuing on but may be problematic.  Check your ["+fileName+"]",ite);
                } // end try/catch
            } // end for key
        } catch (IOException ioe) {
            log.error("Unable to Read File["+fileName+".properties] file",ioe);
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                /** NOOP **/
                if (log.isDebugEnabled()) { log.debug("Error Closing Property File Stream, unexpected",ioe); }
            } // end try/catch
            is = null;
        } // end try/catch
        props = null;

    } // end initConfig

    /**
     * Check if all the items are presented and not empty
     *
     * @param items : Checked items
     * @return :
     * true -> if all the items are presented.
     * false -> if at least one is absent or empty.
     */
    public static boolean isAllExist(@Nullable Object... items) {
        if (items == null) {
            return false;
        }

        for (Object item : items) {
            if (item == null || item.toString().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private static final String ACTIVITIES = "/activities";

    /**
     * Workaround to external stream url - Trimming /activities suffix
     *
     * @param url The input URL
     * @return A valid push URL either for tiles / ext. streams
     */
    public static String normalizeItemUrl(@Nullable String url) {
        return url != null && url.endsWith(ACTIVITIES) ?
                url.substring(0, url.lastIndexOf(ACTIVITIES)) :
                url;
    }

    public static String getJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException ioe) {
            log.error("Unknown Error",ioe);
        } // end try/catch
        return null;
    } // end getJson

} // end class
