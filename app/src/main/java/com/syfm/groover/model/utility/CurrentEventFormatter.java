package com.syfm.groover.model.utility;

/**
 * Created by lycoris on 2016/08/15.
 */

import com.syfm.groover.model.databases.CurrentEvent.CurrentEventData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * CurrentEventFormatter formats arguments to CurrentEvent class object.
 * CurrentEventFormatter is called by CurrentEventUseCase class.
 */
public class CurrentEventFormatter {

    public CurrentEventData getFormattedCurrentEventDataObject(JSONObject json) throws JSONException {
        CurrentEventData eventData = new CurrentEventData();

        json
    }

}
