package io.reflectoring.buckpal.account.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * A money transfer activity between {@link Account}s.
 */
@Value
@RequiredArgsConstructor
public class Activity {

    ActivityId id;

    /**
     * The account that owns this activity.
     */
    @NonNull
    Account.AccountId ownerAccountId;

    /**
     * The debited account. 돈이 들어온 쪽 계정 (자산 증가, 비용 증가)
     */
    @NonNull
    Account.AccountId sourceAccountId;

    /**
     * The credited account. 돈이 나간 쪽 계정 (자산 감소, 수익 증가)
     */
    @NonNull
    Account.AccountId targetAccountId;

    /**
     * The timestamp of the activity.
     */
    @NonNull
    LocalDateTime timestamp;

    /**
     * The money that was transferred between the accounts.
     */
    @NonNull
    Money money;

    public Activity(
            @NonNull Account.AccountId ownerAccountId,
            @NonNull Account.AccountId sourceAccountId,
            @NonNull Account.AccountId targetAccountId,
            @NonNull LocalDateTime timestamp,
            @NonNull Money money
    ) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    @Value
    public static class ActivityId {
        Long value;
    }
}
