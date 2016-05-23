package ir.parsdeveloper.service.impl.business.core;

import ir.parsdeveloper.model.dto.OnlineUser;
import ir.parsdeveloper.service.api.business.core.OnlineUserHandler;
import org.joda.time.DateTime;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
public class DefaultOnlineUserHandler<T extends OnlineUser> implements OnlineUserHandler<T> {

    ConcurrentMap<String, T> lookupTable = new ConcurrentHashMap<String, T>();

    @Override
    @SuppressWarnings("unchecked")
    public T createDefaultOnlineUser(UserDetails userDetails, String userAgent, DateTime loginTime) {
        return (T) OnlineUser.getBuilder()
                .userDetails(userDetails)
                .UserAgent(userAgent)
                .loginTime(loginTime)
                .build();
    }

    @Override
    public boolean addUser(T onlineUser) {
        if (checkNullability(onlineUser)) {
            lookupTable.putIfAbsent(onlineUser.getUserDetails().getUsername(), onlineUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUser(T onlineUser) {
        if (checkNullability(onlineUser)) {
            lookupTable.remove(onlineUser.getUserDetails().getUsername());
            return true;
        }
        return false;
    }


    public boolean removeUser(UserDetails userDetails) {
        if (userDetails != null && userDetails.getUsername() != null) {
            lookupTable.remove(userDetails.getUsername());
            return true;
        }
        return false;
    }

    @Override
    public Iterator<Map.Entry<String, T>> getOnlineUsers() {
        return lookupTable.entrySet().iterator();
    }

    public Integer getOnlineUserCount() {
        return lookupTable.size();
    }

    private boolean checkNullability(T onlineUser) {
        return onlineUser != null &&
                onlineUser.getUserDetails() != null &&
                onlineUser.getUserDetails().getUsername() != null;
    }
}
