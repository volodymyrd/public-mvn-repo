package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Stop recording response.
 */
@AutoValue
public abstract class StopRecordingResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_StopRecordingResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract StopRecordingResponse build();
  }
}
