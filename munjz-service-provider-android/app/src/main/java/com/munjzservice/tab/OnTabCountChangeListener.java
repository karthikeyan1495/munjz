package com.munjzservice.tab;

import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.model.ServiceRequest;

/**
 * Created by mac on 12/16/17.
 */

public interface OnTabCountChangeListener {

        void tabCountChanged(ActiveModel activeModel);
}
