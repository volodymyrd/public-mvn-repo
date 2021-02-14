package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for bridge deletion.
 */
@AutoValue
public abstract class DeleteBridgeRequest {

  public abstract String getBridgeId();

  public static Builder builder() {
    return new AutoValue_DeleteBridgeRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBridgeId(String newBridgeId);

    public abstract DeleteBridgeRequest build();
  }
}
