package ir.parsdeveloper.model.dto;

import org.joda.time.DateTime;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

public class OnlineUser implements Serializable {

    private UserDetails userDetails;
    private String UserAgent;
    private DateTime loginTime;

    public static Builder getBuilder() {
        return new Builder();
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public DateTime getLoginTime() {
        return loginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnlineUser that = (OnlineUser) o;

        if (UserAgent != null ? !UserAgent.equals(that.UserAgent) : that.UserAgent != null) return false;
        if (!loginTime.equals(that.loginTime)) return false;
        return userDetails.equals(that.userDetails);

    }

    @Override
    public int hashCode() {
        int result = userDetails.hashCode();
        result = 31 * result + (UserAgent != null ? UserAgent.hashCode() : 0);
        result = 31 * result + loginTime.hashCode();
        return result;
    }

    public static class Builder {

        private OnlineUser onlineUser;

        public Builder() {
            onlineUser = new OnlineUser();
        }

        public Builder userDetails(UserDetails userDetails) {
            onlineUser.userDetails = userDetails;
            return this;
        }

        public Builder UserAgent(String UserAgent) {
            onlineUser.UserAgent = UserAgent;
            return this;
        }

        public Builder loginTime(DateTime loginTime) {
            onlineUser.loginTime = loginTime;
            return this;
        }

        public OnlineUser build() {
            return onlineUser;
        }
    }
}
