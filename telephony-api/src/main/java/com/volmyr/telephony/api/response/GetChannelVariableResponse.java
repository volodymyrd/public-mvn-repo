package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for getting a channel variable.
 */
@AutoValue
public abstract class GetChannelVariableResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_GetChannelVariableResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract GetChannelVariableResponse build();
  }
}
