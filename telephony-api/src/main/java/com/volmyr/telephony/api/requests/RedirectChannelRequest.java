package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for redirecting a channel.
 */
@AutoValue
public abstract class RedirectChannelRequest {

  public abstract String getChannelId();

  public abstract String getEndpoint();

  public static Builder builder() {
    return new AutoValue_RedirectChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract Builder setEndpoint(String newEndpoint);

    public abstract RedirectChannelRequest build();
  }
}
