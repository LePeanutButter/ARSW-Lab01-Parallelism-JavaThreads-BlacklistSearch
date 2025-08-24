/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] a) {
        HostBlackListsValidator hblv = new HostBlackListsValidator();
        Logger logger = Logger.getLogger(Main.class.getName());
        LocalTime startTime = LocalTime.now();
        List<Integer> blackListOcurrences = hblv.checkHost("202.24.34.55", 100);
        LocalTime endTime = LocalTime.now();
        logger.log(Level.INFO, "The host was found in the following blacklists: {0}", blackListOcurrences);
        Duration duration = Duration.between(startTime, endTime);
        logger.log(Level.INFO, "Total execution time: {0} milliseconds", duration.toMillis());
    }
    
}
