package uk.gov.hmcts.reform.cmc.submit.domain.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeFactory {

    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    private static final ZoneId LOCAL_ZONE = ZoneId.of("Europe/London");

    private LocalDateTimeFactory() {
    }

    public static LocalDateTime nowInLocalZone() {
        return LocalDateTime.now(LOCAL_ZONE);
    }

    public static LocalDate fromLong(Long input) {
        Date date = new Date(input);
        return Instant.ofEpochMilli(date.getTime())
            .atZone(UTC_ZONE)
            .toLocalDate();
    }

}
