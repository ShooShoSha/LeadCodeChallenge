package com.shooshosha.lead;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class LeadCollection {
    private final Logger logger;
    private ArrayList<Lead> leads;

    public LeadCollection() throws IOException {
        this.logger = Logger.getLogger("default-" + ZonedDateTime.now().toEpochSecond() + ".log");
        this.logger.addHandler(new FileHandler(logger.getName()));
        this.leads = new ArrayList<>();
    }

    public boolean add(Lead lead) {
        if (this.leads.contains(lead)) {
            Lead existing = this.leads.get(this.leads.indexOf(lead));
            logger.info("Update lead: " + Lead.getChanges(existing, lead));
            return updateLead(existing, lead);
        }

        logger.info("New lead: " + lead.toString());
        return this.leads.add(lead);
    }

    private boolean updateLead(Lead oldLead, Lead newLead) {
        if (oldLead.compareTo(newLead) <= 0) {
            return this.leads.contains(oldLead) &&
                    this.leads.remove(oldLead) &&
                    this.leads.add(newLead);
        }

        return true;
    }

    public void encodeToJSON(String fileName) throws IOException {
        JSONObject json = new JSONObject();
        JSONArray jsonLeads = new JSONArray();
        for (Lead lead : this.leads) {
            jsonLeads.add(lead.toMap());
        }

        json.put("leads", jsonLeads);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json.toJSONString());
        }
    }
}
