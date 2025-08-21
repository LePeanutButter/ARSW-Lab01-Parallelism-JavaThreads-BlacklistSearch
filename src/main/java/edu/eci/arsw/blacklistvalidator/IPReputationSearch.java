package edu.eci.arsw.blacklistvalidator;

import java.util.ArrayList;
import java.util.List;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IPReputationSearch extends Thread {
    private int startIndex;
    private int endIndex;
    private String ipAddress;
    private HostBlacklistsDataSourceFacade facade;
    private List<Integer> occurrences;

    public IPReputationSearch(int startIndex, int endIndex, String ipAddress, HostBlacklistsDataSourceFacade facade) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipAddress = ipAddress;
        this.facade = facade;
        this.occurrences = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (facade.isInBlackListServer(i, ipAddress)) {
                occurrences.add(i);
            }
        }
    }

    public int getCheckedCount() {
        return endIndex - startIndex;
    }
}
