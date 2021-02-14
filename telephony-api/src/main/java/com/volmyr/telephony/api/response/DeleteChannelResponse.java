package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for deleting a channel.
 */
@AutoValue
public abstract class DeleteChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_DeleteChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract DeleteChannelResponse build();
  }
}
