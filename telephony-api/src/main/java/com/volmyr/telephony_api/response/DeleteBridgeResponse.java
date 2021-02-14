package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for deleting a bridge.
 */
@AutoValue
public abstract class DeleteBridgeResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_DeleteBridgeResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract DeleteBridgeResponse build();
  }
}
