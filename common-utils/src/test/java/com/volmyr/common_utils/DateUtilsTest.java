package com.volmyr.common_utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.Test;

/**
 * Tests for {@link DateUtils}.
 */
public class DateUtilsTest {

  private static final LocalDateTime LDT_2021_2_15_4_0_0 = LocalDateTime.of(2021, 2, 15, 4, 0, 0);

  @Test
  public void shouldConvertLocalDateTimeToMilliseconds() {
    assertThat(LDT_2021_2_15_4_0_0)
        .isEqualTo(DateUtils.toLocalDateTime(DateUtils.toMilliseconds(LDT_2021_2_15_4_0_0)));
  }
}
