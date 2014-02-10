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

package com.jivesoftware.sdk.api.entity;

import java.io.Serializable;

/**
 * Created by rrutan on 2/4/14.
 */
public interface StorageInstance {

    public String getName();

    public void onRegister(Object data);
    public void onUnregister(Object data);
    public void onDelete(Object data);
    public void onFileDownload(Object data);
    public void onDeletePlace(Object data);
    public void onDeleteFile(Object data);
    public void onUpload(Object data);
    public void onUploadVersion(Object data);

} // en
