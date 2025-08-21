/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private static final Logger LOGGER = Logger.getLogger(HostBlackListsValidator.class.getName());

    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @param threadsNumber number of threads to be used
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int threadsNumber){
        LinkedList<Integer> blackListOcurrences = new LinkedList<>();
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
        int totalServers = skds.getRegisteredServersCount();

        int range = totalServers / threadsNumber;
        int remainder = totalServers % threadsNumber;

        List<IPReputationSearch> threads = new LinkedList<>();
        int start = 0;
        
        for (int i = 0; i < threadsNumber; i++) {
            int end = start + range + (i < remainder ? 1 : 0);
            IPReputationSearch thread = new IPReputationSearch(start, end, ipaddress, skds);
            threads.add(thread);
            thread.start();
            start = end;
        }

        for (IPReputationSearch t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        }

        int occurrencesCount = 0;
        int checkedListsCount = 0;
        for (IPReputationSearch t : threads) {
            List<Integer> partial = t.getOccurrences();
            blackListOcurrences.addAll(partial);
            occurrencesCount += partial.size();
            checkedListsCount += t.getCheckedCount();
        }

        if (occurrencesCount >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, totalServers});
        return blackListOcurrences;
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

}
