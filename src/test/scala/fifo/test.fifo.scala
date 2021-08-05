package fifo.test

import fifo.Fifo

import chisel3._
import chisel3.tester._
import org.scalatest.FreeSpec

import treadle._
import chisel3.tester.experimental.TestOptionBuilder._
import org.scalatest._

class Fifo_UnitTester extends FreeSpec with ChiselScalatestTester with Matchers { 
    private val fifo_depth = 32
    val annotations = Seq(
        // VerilatorBackendAnnotation,
        WriteVcdAnnotation
        // VerboseAnnotation
        // SymbolsToWatchAnnotation(Seq("io_out_nfull"))
    )

    "FIFO: First write then read" in {
        test(new Fifo(fifo_depth, 32)).withAnnotations(annotations) { dut => 
            dut.clock.step(10)
            fork {
                for {i <- 1 to 2*fifo_depth } {
                    println("nfull: " + dut.io.out.nfull.peek())
                    while (!dut.io.out.nfull.peek().litToBoolean) {
                        dut.clock.step(1)
                        println("full full")
                    }
                    dut.io.in.wen.poke(1.B)
                    dut.io.in.din.poke(i.U)
                    println("write " + i)
                    dut.clock.step(1)
                    dut.io.in.wen.poke(0.B)
                    dut.clock.step(1)
                }
            }
            fork {
                dut.clock.step(150)
                for {j <- 1 to 2*fifo_depth } {
                    println("nempty: " + dut.io.out.nempty.peek())
                    while (!dut.io.out.nempty.peek().litToBoolean) {
                        dut.clock.step(1)
                        println("not empty")
                    }
                    dut.io.in.ren.poke(1.B)
                    dut.clock.step(1)
                    dut.io.in.ren.poke(0.B)
                    println("read " + dut.io.out.rdata.peek())
                    dut.io.out.rdata.expect(j.U)
                    dut.clock.step(1)
                }   
            }

            .join()

        
        }
    }
    "FIFO: First read then write" in {
        test(new Fifo(fifo_depth, 32)).withAnnotations(annotations) { dut => 
            dut.clock.step(10)
            fork {
                for {j <- 1 to 2*fifo_depth } {
                    println("nempty: " + dut.io.out.nempty.peek())
                    while (!dut.io.out.nempty.peek().litToBoolean) {
                        dut.clock.step(1)
                        println("not empty")
                    }
                    dut.io.in.ren.poke(1.B)
                    dut.clock.step(1)
                    dut.io.in.ren.poke(0.B)
                    println("read " + dut.io.out.rdata.peek())
                    dut.io.out.rdata.expect(j.U)
                    dut.clock.step(1)
                }   
            }
            fork {
                dut.clock.step(150)
                for {i <- 1 to 2*fifo_depth } {
                    println("nfull: " + dut.io.out.nfull.peek())
                    while (!dut.io.out.nfull.peek().litToBoolean) {
                        dut.clock.step(1)
                        println("full full")
                    }
                    dut.io.in.wen.poke(1.B)
                    dut.io.in.din.poke(i.U)
                    println("write " + i)
                    dut.clock.step(1)
                    dut.io.in.wen.poke(0.B)
                    dut.clock.step(1)
                }
            }

            .join()

        
        }
    }

}   
