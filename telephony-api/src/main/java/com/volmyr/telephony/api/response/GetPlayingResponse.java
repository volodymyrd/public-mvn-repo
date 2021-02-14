package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for getting a media.
 */
@AutoValue
public abstract class GetPlayingResponse {

  public abstract FullMapResponse getBasicResponse();

  public abstract String getPlaybackId();

  public abstract String getState();

  public static Builder builder() {
    return new AutoValue_GetPlayingResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract Builder setPlaybackId(String newPlaybackId);

    public abstract Builder setState(String newState);

    public abstract GetPlayingResponse build();
  }
}
