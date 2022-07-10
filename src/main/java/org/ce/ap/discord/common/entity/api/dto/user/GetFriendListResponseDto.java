package org.ce.ap.discord.common.entity.api.dto.user;

import org.ce.ap.discord.common.entity.business.Person;

import java.io.Serializable;
import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetFriendListResponseDto implements Serializable {

    private static final long serialVersionUID = 2325036436038496914L;

    private final List<Person> friendList;

    public GetFriendListResponseDto(List<Person> friendList) {
        this.friendList = friendList;
    }

    public List<Person> getFriendList() {
        return friendList;
    }
}
