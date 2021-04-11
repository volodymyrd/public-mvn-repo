package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;
import java.util.Map;

/**
 * Basic Ari response as a <code>Map{@literal <}String, Object{@literal >}</code>.
 */
@AutoValue
public abstract class FullMapResponse {

  public abstract Map<String, Object> getMap();

  public static Builder builder() {
    return new AutoValue_FullMapResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setMap(Map<String, Object> newMap);

    public abstract FullMapResponse build();
  }
}
