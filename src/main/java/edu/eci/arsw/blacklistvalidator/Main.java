/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

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
        List<Integer> blackListOcurrences = hblv.checkHost("200.24.34.55", 10);
        logger.log(Level.INFO, "The host was found in the following blacklists: {0}", blackListOcurrences);

    }
    
}
