package com.volmyr.common_utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Date utils.
 */
public final class DateUtils {

  public static long toMilliseconds(LocalDateTime localDateTime) {
    return toMilliseconds(localDateTime, ZoneId.systemDefault());
  }

  public static long toMilliseconds(LocalDateTime localDateTime, ZoneId zoneId) {
    return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
  }

  public static LocalDateTime toLocalDateTime(long milliseconds) {
    return toLocalDateTime(milliseconds, ZoneId.systemDefault());
  }

  public static LocalDateTime toLocalDateTime(long milliseconds, ZoneId zoneId) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), zoneId);
  }
}
