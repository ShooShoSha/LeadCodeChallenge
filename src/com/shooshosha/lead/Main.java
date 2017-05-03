package com.shooshosha.lead;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (handleArgs(args)) {
            removeDuplicates(args[0], args[1]);
        }
        else {
            System.out.println("Missing arguments");
            System.out.println("Example usage:");
            System.out.println("\tjava -jar leads.jar input output");
            System.out.println("\tinput:\t\tThe input file path");
            System.out.println("\toutput:\t\tThe output file path");
        }
    }

    private static boolean handleArgs(String[] args) {
        boolean isValidArgs = args.length == 2;

        return isValidArgs;
    }

    private static void removeDuplicates(String fileName, String newFileName) {
        try {
            Lead[] rawLeads = LeadParser.getLeads(fileName);
            LeadCollection leads = new LeadCollection();
            for (int i = 0; i < rawLeads.length; i++) {
                leads.add(rawLeads[i]);
            }

            leads.encodeToJSON(newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
