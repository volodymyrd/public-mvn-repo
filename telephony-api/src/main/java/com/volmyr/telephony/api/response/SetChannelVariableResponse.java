package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for setting a channel variable.
 */
@AutoValue
public abstract class SetChannelVariableResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_SetChannelVariableResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract SetChannelVariableResponse build();
  }
}
