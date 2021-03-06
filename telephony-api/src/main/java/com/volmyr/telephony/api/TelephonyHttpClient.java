package com.volmyr.telephony.api;

import com.volmyr.telephony.api.response.AddChannelsToBridgeResponse;
import com.volmyr.telephony.api.response.AnswerChannelResponse;
import com.volmyr.telephony.api.response.ContinueChannelResponse;
import com.volmyr.telephony.api.response.CreateBridgeResponse;
import com.volmyr.telephony.api.response.CreateChannelResponse;
import com.volmyr.telephony.api.response.DeleteBridgeResponse;
import com.volmyr.telephony.api.response.DeleteChannelResponse;
import com.volmyr.telephony.api.response.DeleteRecordingResponse;
import com.volmyr.telephony.api.response.MoveChannelResponse;
import com.volmyr.telephony.api.response.PlayChannelResponse;
import com.volmyr.telephony.api.response.RecordBridgeResponse;
import com.volmyr.telephony.api.response.RecordChannelResponse;
import com.volmyr.telephony.api.response.RedirectChannelResponse;
import com.volmyr.telephony.api.response.SnoopChannelResponse;
import com.volmyr.telephony.api.response.StopPlayingResponse;
import com.volmyr.telephony.api.response.StopRecordingResponse;
import com.volmyr.telephony.api.requests.AddChannelsToBridgeRequest;
import com.volmyr.telephony.api.requests.AnswerChannelRequest;
import com.volmyr.telephony.api.requests.ContinueChannelRequest;
import com.volmyr.telephony.api.requests.CreateBridgeRequest;
import com.volmyr.telephony.api.requests.CreateChannelRequest;
import com.volmyr.telephony.api.requests.DeleteBridgeRequest;
import com.volmyr.telephony.api.requests.DeleteChannelRequest;
import com.volmyr.telephony.api.requests.GetChannelRequest;
import com.volmyr.telephony.api.requests.GetChannelVariableRequest;
import com.volmyr.telephony.api.requests.GetPlayingRequest;
import com.volmyr.telephony.api.requests.MoveChannelRequest;
import com.volmyr.telephony.api.requests.PingRequest;
import com.volmyr.telephony.api.requests.PlayBridgeRequest;
import com.volmyr.telephony.api.requests.PlayChannelRequest;
import com.volmyr.telephony.api.requests.RecordBridgeRequest;
import com.volmyr.telephony.api.requests.RecordChannelRequest;
import com.volmyr.telephony.api.requests.RedirectChannelRequest;
import com.volmyr.telephony.api.requests.SetChannelVariableRequest;
import com.volmyr.telephony.api.requests.SnoopChannelRequest;
import com.volmyr.telephony.api.requests.StopPlayingRequest;
import com.volmyr.telephony.api.requests.StopRecordingRequest;
import com.volmyr.telephony.api.response.GetChannelResponse;
import com.volmyr.telephony.api.response.GetChannelVariableResponse;
import com.volmyr.telephony.api.response.GetPlayingResponse;
import com.volmyr.telephony.api.response.PingResponse;
import com.volmyr.telephony.api.response.PlayBridgeResponse;
import com.volmyr.telephony.api.response.SetChannelVariableResponse;

/**
 * API for HTTP telephony client.
 */
public interface TelephonyHttpClient {

  String PARENT_ID = "parent_id";

  /**
   * Response pong message.
   */
  PingResponse ping(PingRequest request) throws Exception;

  /**
   * Creates a channel.
   */
  CreateChannelResponse createChannel(CreateChannelRequest request) throws Exception;

  /**
   * Get a channel.
   */
  GetChannelResponse getChannel(GetChannelRequest request) throws Exception;

  /**
   * Sets a channel variable.
   */
  SetChannelVariableResponse setChannelVariable(SetChannelVariableRequest request) throws Exception;

  /**
   * Gets a channel variable.
   */
  GetChannelVariableResponse getChannelVariable(GetChannelVariableRequest request) throws Exception;

  /**
   * Deletes (hangup) a channel.
   */
  DeleteChannelResponse deleteChannel(DeleteChannelRequest request) throws Exception;

  /**
   * Creates a bridge.
   */
  CreateBridgeResponse createBridge(CreateBridgeRequest request) throws Exception;

  /**
   * Deletes (shut down) a bridge.
   */
  DeleteBridgeResponse deleteBridge(DeleteBridgeRequest request) throws Exception;

  /**
   * Adds channels to a bridge.
   */
  AddChannelsToBridgeResponse addChannelsToBridge(AddChannelsToBridgeRequest request)
      throws Exception;

  /**
   * Starts recording a channel.
   */
  RecordChannelResponse recordChannel(RecordChannelRequest request) throws Exception;

  /**
   * Answers a channel.
   */
  AnswerChannelResponse answerChannel(AnswerChannelRequest request) throws Exception;

  /**
   * Moves the channel from one Stasis application to another.
   */
  MoveChannelResponse moveChannel(MoveChannelRequest request) throws Exception;

  /**
   * Starts playback of media in a channel.
   */
  PlayChannelResponse playChannelMedia(PlayChannelRequest request) throws Exception;

  /**
   * Starts playback of media in a bridge.
   */
  PlayBridgeResponse playBridgeMedia(PlayBridgeRequest request) throws Exception;

  /**
   * Stops a playback.
   */
  StopPlayingResponse stopPlayback(StopPlayingRequest request) throws Exception;

  /**
   * Gets a playback.
   */
  GetPlayingResponse getPlayback(GetPlayingRequest request) throws Exception;

  /**
   * Starts recording a channel.
   */
  RecordBridgeResponse recordBridge(RecordBridgeRequest request) throws Exception;

  /**
   * Stops a recording and discards it.
   */
  StopRecordingResponse stopRecordingAndDiscard(StopRecordingRequest request) throws Exception;

  /**
   * Stops a recording.
   */
  StopRecordingResponse stopRecording(StopRecordingRequest request) throws Exception;

  /**
   * Returns a recorded file.
   */
  byte[] getRecordedFile(String fileName) throws Exception;

  /**
   * Deletes a stored recording.
   */
  DeleteRecordingResponse deleteRecording(String fileName) throws Exception;

  /**
   * Starts snooping a channel.
   */
  SnoopChannelResponse startSnooping(SnoopChannelRequest request) throws Exception;

  /**
   * Redirects a channel.
   */
  RedirectChannelResponse redirectChannel(RedirectChannelRequest request) throws Exception;

  /**
   * Exits from an application and continue a channel.
   */
  ContinueChannelResponse continueChannel(ContinueChannelRequest request) throws Exception;
}
