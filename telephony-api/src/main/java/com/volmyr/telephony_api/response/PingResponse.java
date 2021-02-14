package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for ping.
 */
@AutoValue
public abstract class PingResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_PingResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract PingResponse build();
  }
}
