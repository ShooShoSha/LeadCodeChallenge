package com.shooshosha.lead;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

/**
 * Created by shoos on 5/3/2017.
 */
public final class LeadParser {
    private final static String LEADS_ARRAY_VALUE = "leads";

    public static Lead[] getLeads(String JSONFileName) throws IOException {
        String text = getJSONText(JSONFileName);
        JSONObject leadsRoot = getLeadsRoot(text);
        JSONArray leadsArray = getLeads(leadsRoot);
        Lead[] leads = new Lead[leadsArray.size()];
        for (int i = 0; i < leadsArray.size() && i < leads.length; i++) {
            JSONObject aLead = (JSONObject) leadsArray.get(i);
            leads[i] = new Lead.Builder((String) aLead.get("_id"), (String) aLead.get("email"))
                    .firstName((String) aLead.get("firstName"))
                    .lastName((String) aLead.get("lastName"))
                    .entryDate(ZonedDateTime.parse((String) aLead.get("entryDate")))
                    .build();
        }

        return leads;
    }

    private static String getJSONText(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
    }

    private static JSONObject getLeadsRoot(String text) {
        return (JSONObject) JSONValue.parse(text);
    }

    private static JSONArray getLeads(JSONObject leadsRoot) {
        return (JSONArray) leadsRoot.get(LEADS_ARRAY_VALUE);
    }
}
