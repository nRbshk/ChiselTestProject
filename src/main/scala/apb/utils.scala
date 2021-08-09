package apb

import chisel3._
import chisel3.util._
import chisel3.experimental.ChiselEnum

class APB_interface_master(AW: Int = 8, DW: Int = 32) extends Bundle {
    val PCLK = Clock()
    val PRESETn = AsyncReset()
    
    val PADDR = UInt(AW.W)
    val PWRITE = Bool()
    val PSEL = Bool()
    val PENABLE = Bool()
    
    val PWDATA = UInt(DW.W)


}

class APB_interface_slave(DW: Int = 32) extends Bundle {
    val PRDATA = UInt(DW.W)
    val PREADY = Bool()
    val PSLVERR = Bool()
}


object State extends ChiselEnum {
    val IDLE, WR, RD = Value
}