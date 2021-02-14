package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for playing a media in a bridge.
 */
@AutoValue
public abstract class PlayBridgeRequest {

  public abstract String getBridgeId();

  public abstract Iterable<String> getUris();

  public static Builder builder() {
    return new AutoValue_PlayBridgeRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBridgeId(String newBridgeId);

    public abstract Builder setUris(Iterable<String> newUris);

    public abstract PlayBridgeRequest build();
  }
}
