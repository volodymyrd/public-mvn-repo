package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Request for bridge creation.
 */
@AutoValue
public abstract class CreateBridgeRequest {

  public abstract String getType();

  @Nullable
  public abstract String getBridgeId();

  @Nullable
  public abstract String getName();

  public static Builder builder() {
    return new AutoValue_CreateBridgeRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setType(String newType);

    public abstract Builder setBridgeId(String newBridgeId);

    public abstract Builder setName(String newName);

    public abstract CreateBridgeRequest build();
  }
}
