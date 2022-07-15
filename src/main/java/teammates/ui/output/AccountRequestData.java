package teammates.ui.output;

import javax.annotation.Nullable;

import teammates.common.datatransfer.AccountRequestStatus;
import teammates.common.datatransfer.attributes.AccountRequestAttributes;

/**
 * Output format of account request data.
 */
public class AccountRequestData extends ApiOutput {

    private final String name;
    private final String institute;
    private final String email;
    private final String homePageUrl;
    private final String comments;
    private final String registrationKey;
    private final AccountRequestStatus status;
    private final long createdAt;
    @Nullable
    private final Long lastProcessedAt;
    @Nullable
    private final Long registeredAt;

    public AccountRequestData(AccountRequestAttributes accountRequestInfo) {
        this.name = accountRequestInfo.getName();
        this.institute = accountRequestInfo.getInstitute();
        this.email = accountRequestInfo.getEmail();
        this.homePageUrl = accountRequestInfo.getHomePageUrl();
        this.comments = accountRequestInfo.getOtherComments();
        this.registrationKey = accountRequestInfo.getRegistrationKey();
        this.status = accountRequestInfo.getStatus();
        this.createdAt = accountRequestInfo.getCreatedAt().toEpochMilli();
        if (accountRequestInfo.getLastProcessedAt() == null) {
            this.lastProcessedAt = null;
        } else {
            this.lastProcessedAt = accountRequestInfo.getLastProcessedAt().toEpochMilli();
        }
        if (accountRequestInfo.getRegisteredAt() == null) {
            this.registeredAt = null;
        } else {
            this.registeredAt = accountRequestInfo.getRegisteredAt().toEpochMilli();
        }
    }

    public String getName() {
        return name;
    }

    public String getInstitute() {
        return institute;
    }

    public String getEmail() {
        return email;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public String getComments() {
        return comments;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public AccountRequestStatus getStatus() {
        return status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Long getLastProcessedAt() {
        return lastProcessedAt;
    }

    public Long getRegisteredAt() {
        return registeredAt;
    }

}
