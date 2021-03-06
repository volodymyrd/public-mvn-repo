package com.volmyr.oauth2_utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.Nullable;

/**
 * Helper class for working with Google OAuth2 library.
 */
public final class GoogleOAuth2Utils {

  @Nullable
  public static String getToken(@Nullable String clientId, Collection<String> scopes)
      throws IOException {
    return getToken(clientId, null, scopes);
  }

  @Nullable
  public static String getToken(
      @Nullable String clientId,
      @Nullable String pathToFileWithCredentials,
      Collection<String> scopes)
      throws IOException {
    if (isNullOrEmpty(clientId)) {
      return null;
    }
    checkArgument(scopes != null && !scopes.isEmpty(), "Parameter 'scopes' must be set");

    GoogleCredentials credentials = getGoogleCredentials(pathToFileWithCredentials, scopes);

    checkNotNull(credentials, "Expected to load credentials");
    checkState(
        credentials instanceof IdTokenProvider,
        String.format(
            "Expected credentials that can provide id tokens, got %s instead",
            credentials.getClass().getName()));

    return IdTokenCredentials.newBuilder()
        .setIdTokenProvider((IdTokenProvider) credentials)
        .setTargetAudience(clientId)
        .build().refreshAccessToken().getTokenValue();
  }

  private static GoogleCredentials getGoogleCredentials(
      @Nullable String pathToFileWithCredentials, Collection<String> scopes) throws IOException {
    if (!isNullOrEmpty(pathToFileWithCredentials)) {
      try (FileInputStream fileInputStream = new FileInputStream(pathToFileWithCredentials)) {
        return GoogleCredentials.fromStream(fileInputStream);
      }
    }
    return GoogleCredentials.getApplicationDefault().createScoped(scopes);
  }

  private GoogleOAuth2Utils() {
  }
}
