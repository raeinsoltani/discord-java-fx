package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.Person;

import java.io.Serializable;
import java.util.List;

public class GetServerMembersResponseDto implements Serializable {

    private static final long serialVersionUID = 2247130904505888253L;

    List<Person> serverMembersList;
    private final boolean successful;
    private final String failureCause;

    public GetServerMembersResponseDto(boolean successful, String failureCause, List<Person> personList) {
        this.serverMembersList = personList;
        this.failureCause = failureCause;
        this.successful = successful;
    }

    public List<Person> getServerMembersList() {
        return serverMembersList;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
