package fifo.test

import fifo.Fifo_sp

import chisel3._
import chisel3.tester._
import org.scalatest.FreeSpec

import treadle._
import chisel3.tester.experimental.TestOptionBuilder._

class Fifo_UnitTester extends FreeSpec with ChiselScalatestTester { 
    private val fifo_depth = 32
    val annotations = Seq(
        // VerilatorBackendAnnotation,
    //   WriteVcdAnnotation,
        VerboseAnnotation,
        SymbolsToWatchAnnotation(Seq("io_out"))
    )
    test(new Fifo(fifo_depth, 32)).withAnnotations(annotations) { dut => 
        for {i <- 1 to fifo_depth } {
            dut.io.in.wen.poke(1.B)
            dut.io.in.din.poke(i.U)
            dut.io.clk.step(1)
            dut.io.in.wen.poke(0.B)
            dut.io.clk.step(1)
        }
        for {i <- 1 to fifo_depth } {
            dut.io.in.ren.poke(1.B)
            dut.io.clk.step(1)
            dut.io.in.ren.poke(0.B)
            dut.io.out.rdata.expect(i.U)
            dut.io.clk.step(1)
        }   
    
    }

}   
