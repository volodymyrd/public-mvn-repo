package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;

/**
 * Stop playing request.
 */
@AutoValue
public abstract class StopRecordingRequest {

  public abstract String getFileName();

  public static Builder builder() {
    return new AutoValue_StopRecordingRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setFileName(String newFileName);

    public abstract StopRecordingRequest build();
  }
}
