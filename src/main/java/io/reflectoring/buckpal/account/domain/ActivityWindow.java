package io.reflectoring.buckpal.account.domain;


import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A window of account activities.
 */
public class ActivityWindow {

    private final List<Activity> activities;

    public LocalDateTime getStartTimestamp() {
        return activities.stream()
                .min(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    public LocalDateTime getEndTimeStamp() {
        return activities.stream()
                .max(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    public Money calculateBalance(Account.AccountId accountId) {
        // 나에게 들어온 돈 (입금 총합)
        Money depositBalance = activities.stream()
                .filter(a -> a.getTargetAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);

        // 나에게서 나간 돈 (출금 총합)
        Money withdrawBalance = activities.stream()
                .filter(a -> a.getSourceAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);

        // 최종 잔액 = 입금 - 출금
        return Money.add(depositBalance, withdrawBalance.negative());
    }

    public ActivityWindow(@NonNull List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * ...은 가변인수라고 하며(Variable Arguments, 줄여서 varargs) 타입... 매개변수명 으로 사용
     * 컴파일시, 배열로 처리
     * 성능 이슈가 있을 수도 있음(항상 배열을 새로 만들어서 할당하고 초기화 하므로)
     * ex) new ActivityWindow(a1, a2, a3)
     */
    public ActivityWindow(@NonNull Activity... activities) {
        this.activities = new ArrayList<>(Arrays.asList(activities));
    }

    public List<Activity> getActivities() {
        // 읽기 전용 list 로 변환, get 이외에 메서드 호출 시 UnsupportedOperationException 발생
        return Collections.unmodifiableList(this.activities);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

}
