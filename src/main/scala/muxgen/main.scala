package muxgen

import chisel3._
import chisel3.Driver._


object MuxGenApp extends App {
    chisel3.Driver.execute(args, () => new MuxN(5))
    chisel3.Driver.execute(args, () => new NamedMuxN(Nat(5)))
}

