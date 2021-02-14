package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Request for moving the channel from one Stasis application to another.
 */
@AutoValue
public abstract class MoveChannelRequest {

  public abstract String getChannelId();

  public abstract String getApp();

  @Nullable
  public abstract String getAppArgs();
}
