package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for getting a channel variable.
 */
@AutoValue
public abstract class GetChannelVariableRequest {

  public abstract String getChannelId();

  public abstract String getVariable();

  public static Builder builder() {
    return new AutoValue_GetChannelVariableRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract Builder setVariable(String newVariable);

    public abstract GetChannelVariableRequest build();
  }
}
