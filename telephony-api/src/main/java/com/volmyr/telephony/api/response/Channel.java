package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;
import java.util.Date;

/**
 * Channel.
 */
@AutoValue
public abstract class Channel {

  public enum State {
    RING("Ring"),
    RINGING("Ringing"),
    UP("Up"),
    DOWN("Down");

    private String state;

    State(String state) {
      this.state = state;
    }

    public String getState() {
      return state;
    }
  }

  public abstract String getId();

  public abstract String getName();

  public abstract State getState();

  public abstract Date getCreationTime();

  public abstract String getLanguage();

  //to
  public abstract Participant getCaller();

  //from
  public abstract Participant getConnected();

  public abstract Dialplan getDialplan();

  public static Builder builder() {
    return new AutoValue_Channel.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setId(String newId);

    public abstract Builder setName(String newName);

    public abstract Builder setState(State newState);

    public abstract Builder setCreationTime(Date newCreationTime);

    public abstract Builder setLanguage(String newLanguage);

    public abstract Builder setCaller(Participant newCaller);

    public abstract Builder setConnected(Participant newConnected);

    public abstract Builder setDialplan(Dialplan newDialplan);

    public abstract Channel build();
  }
}
