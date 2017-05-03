package com.shooshosha.lead;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lead implements Comparable<Lead> {
    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final ZonedDateTime entryDate;

    private Lead(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.entryDate = builder.entryDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Lead lead = (Lead) other;
        return Objects.equals(this.id, lead.id) ||
                Objects.equals(this.email, lead.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("id: ").append(id).append(", ");
        sb.append("email: ").append(email).append(", ");
        sb.append("firstName: ").append(firstName).append(", ");
        sb.append("lastName: ").append(lastName).append(", ");
        sb.append("entryDate: ").append(entryDate);
        return sb.toString();
    }

    @Override
    public int compareTo(Lead o) {
        return this.entryDate.compareTo(o.entryDate);
    }

    public static String getChanges(Lead oldLead, Lead newLead) {
        final StringBuffer changes = new StringBuffer();

        changes.append(getFieldChange("_id", oldLead.id, newLead.id)).append(", ");
        changes.append(getFieldChange("email", oldLead.email, newLead.email)).append(", ");
        changes.append(getFieldChange("firstName", oldLead.firstName, newLead.firstName)).append(", ");
        changes.append(getFieldChange("lastName", oldLead.lastName, newLead.lastName)).append(", ");
        changes.append(getFieldChange("entryDate", oldLead.entryDate.toString(), newLead.entryDate.toString()));

        return changes.toString();
    }

    private static String getFieldChange(String fieldName, String oldValue, String newValue) {
        String change = fieldName + ": " + newValue;
        if (oldValue.equals(newValue) == false) {
            change += " [old: " + oldValue + ']';
        }

        return change;
    }

    public Map toMap() {
        HashMap<String, String> leadAsMap = new HashMap<>();
        leadAsMap.put("_id", this.id);
        leadAsMap.put("email", this.email);
        leadAsMap.put("firstName", this.firstName);
        leadAsMap.put("lastName", this.lastName);
        leadAsMap.put("entryDate", this.entryDate.toString());
        return leadAsMap;
    }

    public static class Builder {
        private final String id;
        private final String email;
        private String firstName;
        private String lastName;
        private ZonedDateTime entryDate;

        public Lead build() {
            return new Lead(this);
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder entryDate(ZonedDateTime entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder(String id, String email) {
            if (id == null || id.length() == 0) {
                throw new IllegalArgumentException("id is blank");
            }

            if (email == null || email.length() == 0) {
                throw new IllegalArgumentException("email is blank");
            }

            this.id = id;
            this.email = email;
            this.entryDate = ZonedDateTime.now();
        }
    }
}
