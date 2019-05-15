package com.sx.pcap

 class pcapFileHead {
  var biaozhi: Int = 0
  var maiVer: Short = 0
  var minorver: Short = 0
  var time: Int = 0
  var stampOfTime: Int = 0
  var maxlen: Int = 0
  var typeOflink: Int =0
}
class pcapDataHead{
  var timeStamp : Int = 0
  var timeMs : Int = 0
  var capLen : Int = 0
  var len : Int = 0
}

class tcpHead{
  var sourcePort: Short = 0
  var dstPort : Short = 0
  var seqNumber : Int = 0
  var ack : Int = 0
  var tcpHeadLen : Byte = 0
  var flag : Byte = 0
  var window : Short = 0
  var tcpCheckSum : Short = 0
  var urgentPoints : Short = 0
}

class udpHead {
  var sourcePort : Short = 0
  var desPort : Short = 0
  var udpLen : Short = 0
  var udpCheckSum : Short = 0
}

class IPhead {
  var ipHeadLen : Byte = 0
  var typeOfService : Byte = 0
  var totalLen : Short = 0
  var biaozhi : Short = 0
  var flag : Short = 0
  var ttl : Byte = 0
  var protocol : Byte = 0
  var checkSum : Short = 0
  var srcIp : Int = 0
  var dstIp : Int = 0
}
class macHead{
  var desMac : Byte = 0
  var srcMac : Byte = 0
  var frameType : Short = 0
}