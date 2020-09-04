package com.volmyr.oauth2_utils;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link GoogleOAuth2Utils}.
 */
public class GoogleOAuth2UtilsTest {

  @Test
  @Ignore
  public void shouldReturnValidToken() throws IOException {
    Assertions.assertThat(GoogleOAuth2Utils.getToken(
        "provide valid client id",
        "provide path to an json file with google credentials",
        ImmutableList.of("https://www.googleapis.com/auth/cloud-platform.read-only")))
    .isNotEmpty();
  }
}
