package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for ping.
 */
@AutoValue
public abstract class PingRequest {

  public static Builder builder() {
    return new AutoValue_PingRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract PingRequest build();
  }
}
