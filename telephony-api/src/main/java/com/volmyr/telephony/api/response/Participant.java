package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Participant whether a caller (to) or calling (connected, from).
 */
@AutoValue
public abstract class Participant {

  public abstract String getName();

  public abstract String getNumber();

  public static Builder builder() {
    return new AutoValue_Participant.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setName(String newName);

    public abstract Builder setNumber(String newNumber);

    public abstract Participant build();
  }
}
