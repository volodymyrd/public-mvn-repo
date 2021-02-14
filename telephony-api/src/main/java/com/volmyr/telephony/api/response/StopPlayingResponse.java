package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for stopping playing a media.
 */
@AutoValue
public abstract class StopPlayingResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_StopPlayingResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract StopPlayingResponse build();
  }
}
