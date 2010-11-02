/*
 * Copyright (c) 2006-2007 Luciano Broussal <luciano.broussal AT gmail.com>
 * (http://www.gwtwindowmanager.org)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwm.client.impl;

import org.gwm.client.GFrame;
import org.gwm.client.event.GFrameAdapter;
import org.gwm.client.event.GFrameListener;

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;


public class CoadSelectBoxManagerImpl {
    public CoadSelectBoxManagerImpl() {
    }

    
    public void setBlockerVisible(boolean visible) {
    }


    public void setLocation(int top, int left , GFrame associatedFrame) {
    }


    public void setBlockerSize(int width, int height) {
    }


    public void setBlockerDeepLayer(int i) {
    }


    public void onParentDragEnd(CoadFrame parent) {
        setSelectionActive(true);
        Widget content = parent.getContent();
        if(content instanceof Frame){
            content.setVisible(true);
        }
    }


    public void onParentDragStart(CoadFrame parent) {
        setSelectionActive(false);
        Widget content = parent.getContent();
        if(content instanceof Frame){
            content.setVisible(false);
        }
    }


    public void removeBlocker() {
    }


    public GFrameListener getFrameListener() {
        return new GFrameAdapter();
    }
    
    public native void setSelectionActive(boolean active)/*-{
        if(active){
            $doc.body.ondrag = function () { $wnd.alert('drag'); return false; };
            $doc.body.onselectstart = function () { return false; };
        }else{
            $doc.body.ondrag = null;
            $doc.body.onselectstart = null;
        }
    }-*/;

    public Widget getBlockerWidget(){
        return null;
    }
}
