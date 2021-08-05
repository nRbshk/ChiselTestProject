package fifo

import chisel3._
import chisel3.util._


class Fifo_interface_in(WIDTH: Int = 32) extends Bundle {
    val wen = Bool()
    val din = UInt(WIDTH.W)
    val ren = Bool()


}

class Fifo_interface_out(WIDTH: Int = 32) extends Bundle {
    val rdata = UInt(WIDTH.W)

    val nempty = Bool()
    val nfull = Bool()
}
