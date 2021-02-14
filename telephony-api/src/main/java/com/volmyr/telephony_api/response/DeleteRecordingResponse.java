package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;

/**
 * Response for deleting a stored recording.
 */
@AutoValue
public abstract class DeleteRecordingResponse {

  public abstract FullMapResponse getBasicResponse();

  public static Builder builder() {
    return new AutoValue_DeleteRecordingResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract DeleteRecordingResponse build();
  }
}
