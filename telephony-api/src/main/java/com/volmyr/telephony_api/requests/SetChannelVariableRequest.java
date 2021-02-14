package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for setting a channel variable.
 */
@AutoValue
public abstract class SetChannelVariableRequest {

  public abstract String getChannelId();

  public abstract String getVariable();

  public abstract String getValue();

  public static Builder builder() {
    return new AutoValue_SetChannelVariableRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract Builder setVariable(String newVariable);

    public abstract Builder setValue(String newValue);

    public abstract SetChannelVariableRequest build();
  }
}
