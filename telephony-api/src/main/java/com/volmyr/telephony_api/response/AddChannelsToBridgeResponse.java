package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for adding channels to a bridge.
 */
@AutoValue
public abstract class AddChannelsToBridgeResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_AddChannelsToBridgeResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract AddChannelsToBridgeResponse build();
  }
}
