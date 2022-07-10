package org.ce.ap.discord.common.entity.business.discord;

import org.ce.ap.discord.common.entity.business.enumeration.Ability;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

    private static final long serialVersionUID = -7125453087226744668L;

    List<Ability> abilities;

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

}
