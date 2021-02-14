package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for channel deletion.
 */
@AutoValue
public abstract class DeleteChannelRequest {

  public abstract String getChannelId();

  public static Builder builder() {
    return new AutoValue_DeleteChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract DeleteChannelRequest build();
  }
}
