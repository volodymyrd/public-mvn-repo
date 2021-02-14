package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for creating a bridge.
 */
@AutoValue
public abstract class CreateBridgeResponse {

  public abstract String getBridgeId();

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_CreateBridgeResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBridgeId(String newBridgeId);

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract CreateBridgeResponse build();
  }
}
