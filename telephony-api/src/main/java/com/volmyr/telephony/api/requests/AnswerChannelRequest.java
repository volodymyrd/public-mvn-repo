package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for a channel answering.
 */
@AutoValue
public abstract class AnswerChannelRequest {

  public abstract String getChannelId();

  public static Builder builder() {
    return new AutoValue_AnswerChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract AnswerChannelRequest build();
  }
}
