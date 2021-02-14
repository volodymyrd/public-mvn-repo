package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for recording a channel.
 */
@AutoValue
public abstract class MoveChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_MoveChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract MoveChannelResponse build();
  }
}
