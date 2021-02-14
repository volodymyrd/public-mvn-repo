package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for continue a channel.
 */
@AutoValue
public abstract class ContinueChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_ContinueChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract ContinueChannelResponse build();
  }
}
