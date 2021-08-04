import chisel3.stage.ChiselStage
import fifo._


object Test extends App {
    // (new ChiselStage).emitVerilog(new Fifo_sp(32, 32), Array("-td", "srcv/fifo", "--emit-modules", "verilog"))
    // (new ChiselStage).execute(args, () => new Test_Fifo_sp)
}