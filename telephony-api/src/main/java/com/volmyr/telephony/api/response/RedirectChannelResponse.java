package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for redirecting a channel.
 */
@AutoValue
public abstract class RedirectChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_RedirectChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract RedirectChannelResponse build();
  }
}
