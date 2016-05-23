package ir.parsdeveloper.service.api.business.core;

import ir.parsdeveloper.model.dto.OnlineUser;
import org.joda.time.DateTime;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Iterator;
import java.util.Map;

public interface OnlineUserHandler<T extends OnlineUser> {

    T createDefaultOnlineUser(UserDetails userDetails, String userAgent, DateTime loginTime);

    boolean addUser(T onlineUser);

    boolean removeUser(T onlineUser);

    boolean removeUser(UserDetails userDetails);

    Iterator<Map.Entry<String, T>> getOnlineUsers();

    Integer getOnlineUserCount();
}
