package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for getting a channel.
 */
@AutoValue
public abstract class GetChannelRequest {

  public abstract String getChannelId();

  public static Builder builder() {
    return new AutoValue_GetChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract GetChannelRequest build();
  }
}
