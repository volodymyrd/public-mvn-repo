package com.volmyr.common_utils;

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet helper methods.
 */
public final class ServletUtils {

  /**
   * Tries to retrieve a remote host IP address.
   */
  public static @Nullable
  String getIpOfRemoteHost(HttpServletRequest request) {
    String remoteAddress = request.getHeader("X-FORWARDED-FOR");
    if (isNullOrEmpty(remoteAddress)) {
      remoteAddress = request.getRemoteAddr();
    }
    return remoteAddress;
  }

  private ServletUtils() {
  }
}
