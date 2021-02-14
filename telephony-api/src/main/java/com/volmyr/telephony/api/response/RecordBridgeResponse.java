package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for recording a bridge.
 */
@AutoValue
public abstract class RecordBridgeResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_RecordBridgeResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract RecordBridgeResponse build();
  }
}
