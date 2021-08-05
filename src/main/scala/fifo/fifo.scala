package fifo

import chisel3._
import chisel3.util._


class Fifo(DEPTH: Int = 32, WIDTH: Int = 32) extends Module {
    val io = IO(new Bundle {
        // val clk = Input(Clock())
        val rstn = Input(AsyncReset())

        val in = Input(new Fifo_interface_in(WIDTH))
        val out = Output(new Fifo_interface_out(WIDTH))
    })

    val depth: Int = log2Ceil(DEPTH) + 1


    val clock_block = withClockAndReset(clock, io.rstn){
        val ptr_r = RegInit(0.U(depth.W))

        when (io.in.ren) { ptr_r := ptr_r + 1.U } 

        val ptr_w = RegInit(0.U(depth.W))

        when (io.in.wen) { ptr_w := ptr_w + 1.U } 

        val data = SyncReadMem(DEPTH, UInt(WIDTH.W))

        when (io.in.wen) {  data.write(ptr_w, io.in.din) }
        

        io.out.nempty := Mux(ptr_r === ptr_w, 0.B, 1.B)
        io.out.nfull := Mux((ptr_w =/= ptr_r) && (ptr_r(depth-2,0) === ptr_w(depth-2,0)), 0.B, 1.B)
        
        io.out.rdata := data.read(ptr_r, io.in.ren)

    }

    
}
