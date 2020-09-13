package muxgen

import chisel3._
import chisel3.util.log2Up

/*
* NamedMux module for any given number of inputs
* @NNat: number of inputs coded in type system
* @nat:  indicator of number of inputs
* @io:   MuxIO[T](n)
*/
class NamedMuxN[NNat <: Nat](val nat: NNat) extends Module {
    val n = nat.asInt
    assert(n > 1)
    override def desiredName = s"NamedMux${n.toString}"
    val io = IO(MuxIO(n))
    val selWidth = io.sel.getWidth
    // the number of inputs in low range
    val nL = (1 << (log2Up(n) - 1))
    // groups the inputs into those in low range and those in high range
    val xss = io.xs.grouped(nL).toSeq
    // generate two smalled NamesMuxes for the two ranges
    val ys = xss.map((xs: Seq[Clock]) => NamedMuxN(io.sel(selWidth - 2, 0), xs))
    // generate a mux2 for the final output
    io.y := ClockMux2(io.sel(selWidth - 1), ys)
}


/*
* Method object for the logic to generate a NamedMux
*/
object NamedMuxN {
    /*
    * Returns the output of the NamedMux.
    * The number of inputs must be larger than 0.
    * @sel: selection signal
    * @xs:  vector of inputs
    */
    def apply(sel: UInt, xs: Seq[Clock]): Clock = {
        val n = xs.length
        assert(n > 0)
        n match {
            // when n is 1, use the input directly and finish recursion
            case 1 => xs(0)
            // when n is 2, generate a mux2 and finish recursion
            case 2 => ClockMux2(sel, xs)
            // otherwise, continue recursion by generating a smaller NamedMuX
            case _ => {
                val m = Module(new NamedMuxN(Nat(n))).io
                m.sel := sel
                m.xs := xs
                m.y
            }
        }
    }
}
