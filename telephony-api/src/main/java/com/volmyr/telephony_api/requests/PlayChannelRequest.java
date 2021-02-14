package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for playing a media in a channel .
 */
@AutoValue
public abstract class PlayChannelRequest {

  public abstract String getChannelId();

  public abstract Iterable<String> getUris();

  public static Builder builder() {
    return new AutoValue_PlayChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract Builder setUris(Iterable<String> newUris);

    public abstract PlayChannelRequest build();
  }
}
