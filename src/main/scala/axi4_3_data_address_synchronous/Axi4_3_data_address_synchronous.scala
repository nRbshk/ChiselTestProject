package axi4_3_data_address_synchronous


import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage


class Axi4_3_data_address_synchronous(QUEUE_WIDTH: Int = 8) extends RawModule {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rstn = Input(AsyncReset())

    val wvalid = Input(Bool())
    val wready = Input(Bool())
    val wlast = Input(Bool())

    val awvalid = Input(Bool())
    val awready = Input(Bool())

    val wvalid_o = Output(Bool())
    val wready_o = Output(Bool())
    val wlast_o = Output(Bool())

    val awvalid_o = Output(Bool())
    val awready_o = Output(Bool())
  })
  

  val data_accept = Wire(Bool())
  val address_accept = Wire(Bool())
  data_accept := io.wvalid && io.wready
  address_accept := io.awvalid && io.awready

  val output_address_accept = Wire(Bool())
  output_address_accept := io.awready_o && io.awvalid_o

  val ren = Wire(Bool())
  val wen = Wire(Bool())
  ren := data_accept && io.wlast && ~output_address_accept
  wen := address_accept

  val queue = Module(new Queue_Model(4))

  val full = Wire(Bool())
  val nempty = Wire(Bool())

  queue.io.clk := io.clk
  io.rstn <> queue.io.rstn
  ren <> queue.io.ren
  wen <> queue.io.wen
  full <> queue.io.full
  nempty <> queue.io.nempty


  io.wvalid_o := nempty && io.wvalid && ~output_address_accept;
  io.wready_o := nempty && io.wready && ~output_address_accept;
  io.wlast_o := nempty && io.wlast && ~output_address_accept;

  io.awready_o := ~full && io.awready;
  io.awvalid_o := ~full && io.awvalid;

}

object Test extends App {
  (new ChiselStage).emitVerilog(new Axi4_3_data_address_synchronous, Array("-td", "srcv/Axi4_3_data_address_synchronous", "--emit-modules", "verilog"))
}