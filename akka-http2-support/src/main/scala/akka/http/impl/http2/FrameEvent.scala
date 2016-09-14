package akka.http.impl.http2

import akka.http.impl.http2.Protocol.FrameType
import akka.http.impl.http2.Protocol.SettingIdentifier
import akka.util.ByteString

sealed trait FrameEvent
sealed trait StreamFrameEvent extends FrameEvent {
  def streamId: Int
}

final case class DataFrame(
  streamId:  Int,
  endStream: Boolean,
  payload:   ByteString) extends StreamFrameEvent

final case class HeadersFrame(
  streamId:            Int,
  endStream:           Boolean,
  endHeaders:          Boolean,
  headerBlockFragment: ByteString) extends FrameEvent

//final case class PriorityFrame(streamId: Int, streamDependency: Int, weight: Int) extends StreamFrameEvent
//final case class RstStreamFrame(streamId: Int, errorCode: Int) extends StreamFrameEvent
final case class SettingsFrame(settings: Seq[Setting]) extends FrameEvent
case object SettingsAckFrame extends FrameEvent
//case class PushPromiseFrame(streamId: Int) extends StreamFrameEvent
//case class PingFrame(streamId: Int) extends StreamFrameEvent
//case class GoAwayFrame(streamId: Int) extends StreamFrameEvent
final case class WindowUpdateFrame(streamId: Int, windowSizeIncrement: Int) extends StreamFrameEvent
final case class ContinuationFrame(streamId: Int, endHeaders: Boolean, payload: ByteString) extends StreamFrameEvent

final case class Setting(identifier: SettingIdentifier, value: Int)

/** Dummy event for all unknown frames */
final case class UnknownFrameEvent(tpe: FrameType, flags: Int, streamId: Int, payload: ByteString) extends StreamFrameEvent