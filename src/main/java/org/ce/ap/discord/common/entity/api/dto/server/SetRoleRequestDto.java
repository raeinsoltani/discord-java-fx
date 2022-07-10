package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.enumeration.Ability;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class SetRoleRequestDto implements Serializable {

    private static final long serialVersionUID = 4930367415423457679L;

    private final String doerId;
    private final String serverId;
    private final String personId;
    private final ArrayList<Ability> abilities;

    public SetRoleRequestDto(String doerId, String serverId, String personId, ArrayList<Ability> abilities) {
        this.doerId = doerId;
        this.serverId = serverId;
        this.personId = personId;
        this.abilities = abilities;
    }

    public String getDoerId() {
        return doerId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getPersonId() {
        return personId;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }
}
