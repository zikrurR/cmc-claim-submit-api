package uk.gov.hmcts.reform.cmc.submit.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static java.time.Month.FEBRUARY;
import static org.assertj.core.api.Assertions.assertThat;

public class DatesProviderTest {

    @Test
    public void toDateTimeShouldThrowNullPointerWhenGivenNullValue() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            DatesProvider.toDateTime(null);
        });
    }

    @Test
    public void toDateTimeShouldThrowParseExceptionWhenGivenInvalidValue() {

        Assertions.assertThrows(DateTimeParseException.class, () -> {
            DatesProvider.toDateTime("I'm not a date time string");
        });
    }

    @Test
    public void toDateTimeShouldCorrectlyParseValidDateTimeString() {
        LocalDateTime dateTime = DatesProvider.toDateTime("2018-02-10 12:34");
        assertThat(dateTime.getYear()).isEqualTo(2018);
        assertThat(dateTime.getMonth()).isEqualTo(FEBRUARY);
        assertThat(dateTime.getDayOfMonth()).isEqualTo(10);
        assertThat(dateTime.getHour()).isEqualTo(12);
        assertThat(dateTime.getMinute()).isEqualTo(34);
    }

    @Test
    public void toDateShouldThrowNullPointerWhenGivenNullValue() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            DatesProvider.toDate(null);
        });
    }

    public void toDateShouldThrowParseExceptionWhenGivenInvalidValue() {
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            DatesProvider.toDate("I'm not a date time string");
        });
    }

    @Test
    public void toDateShouldCorrectlyParseValidDateTimeString() {
        LocalDate date = DatesProvider.toDate("2018-02-10");
        assertThat(date.getYear()).isEqualTo(2018);
        assertThat(date.getMonth()).isEqualTo(FEBRUARY);
        assertThat(date.getDayOfMonth()).isEqualTo(10);
    }

}
