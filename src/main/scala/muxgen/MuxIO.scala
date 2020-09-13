package muxgen

import chisel3._
import chisel3.util.log2Up

class MuxIO(val n: Int) extends Bundle {
    val sel = Input(UInt(log2Up(n).W))
    val xs  = Input(Vec(n, Clock()))
    val y   = Output(Clock())
}

object MuxIO {
    def apply(n: Int) = {
        new MuxIO(n)
    }
}
