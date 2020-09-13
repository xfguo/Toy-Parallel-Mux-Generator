package muxgen

import chisel3._
import chisel3.experimental._

// Comment the following class to make Mux2 a module.
// Uncomment the following class to make Mux2 a black box.
/*
class Mux2[T <: Clock](private val gen: T) extends BlackBox {
    val io = IO(MuxIO(gen, 2))
    io.y := Mux(io.sel === 1.U, io.xs(1), io.xs(0))
}
*/


// Comment the following class to make Mux2 a balck box.
// Uncomment the following class to make Mux2 a module.
// class Mux2[T <: Clock](private val gen: T) extends Module {
//     val io = IO(MuxIO(2))
//     io.y := Mux(io.sel === 1.U, io.xs(1), io.xs(0))
// }

class clock_mux2 extends ExtModule {
    val clk_sel = IO(Input(UInt(1.W)))
    val clk_in0 = IO(Input(Clock()))
    val clk_in1 = IO(Input(Clock()))
    val clk_out = IO(Output(Clock()))
}

object ClockMux2 {
    def apply(sel: UInt, xs: Seq[Clock]) = {
        val mux2 = Module(new clock_mux2)

	mux2.clk_sel := sel
	mux2.clk_in0 := xs(0)
	mux2.clk_in1 := xs(1)
	mux2.clk_out
    }
}
