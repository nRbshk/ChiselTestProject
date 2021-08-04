package axi4_3_data_address_synchronous

import chisel3._
import chisel3.util._



class Queue_Model(QUEUE_WIDTH: Int = 8) extends RawModule {
    val io = IO(new Bundle{
        val clk = Input(Clock())
        val rstn = Input(AsyncReset())

        val ren = Input(Bool())
        val wen = Input(Bool())

        val full = Output(Bool())
        val nempty = Output(Bool())

    })

    val width = log2Ceil(QUEUE_WIDTH + 1) - 1

    val ptr_r_next = Wire(UInt(width.W))
    val ptr_w_next = Wire(UInt(width.W))

    val block = withClockAndReset(clock = io.clk, reset=io.rstn){
        // Рабочая версия
        // Здесь все ок - генерится queue[QUEUE_WIDTH-1:0]
        val queue_next = Wire(Vec(QUEUE_WIDTH, UInt(1.W)))
        
        val queue = RegEnable(next=queue_next.asUInt, init=0.U(QUEUE_WIDTH.W), enable=(io.ren && io.nempty) || (io.wen && ~io.full))
        val ptr_r = RegEnable(next=ptr_r_next, init=0.U(width.W), enable=(io.ren && io.nempty))
        val ptr_w = RegEnable(next=ptr_w_next, init=0.U(width.W), enable=(io.wen && ~io.full))



        when (io.wen && ~io.full) { queue_next := queue.asTypeOf(Vec(QUEUE_WIDTH, UInt(1.W))); queue_next(ptr_w) := 1.U(1.W) }
        .elsewhen (io.ren && io.nempty) { queue_next := queue.asTypeOf(Vec(QUEUE_WIDTH, UInt(1.W))); queue_next(ptr_r) := 0.U(1.W) }
        .otherwise { queue_next := queue.asTypeOf(Vec(QUEUE_WIDTH, UInt(1.W))) }

        // Конец рабочей версии

        // "Рабочая" версия
        // Здесь генерится queue_T1, queue_T2, queue_T3 ... и тд (несовсем читабельно)
        // val queue = Reg(Vec(QUEUE_WIDTH, UInt(1.W)))
        // val ptr_r = RegEnable(next=ptr_r_next, init=0.U(width.W), enable=(io.ren && io.nempty))
        // val ptr_w = RegEnable(next=ptr_w_next, init=0.U(width.W), enable=(io.wen && ~io.full))

        // when (io.wen && ~io.full) { queue := queue; queue(ptr_w) := 1.U(1.W) }
        // .elsewhen (io.ren && io.nempty) { queue := queue; queue(ptr_r) := 0.U(1.W) }



        ptr_r_next := ptr_r + 1.U(1.W)
        ptr_w_next := ptr_w + 1.U(1.W)

        io.full := queue.asUInt.andR
        io.nempty := queue.asUInt.orR
    }

}

// Что мне надо
// always @(posedge aclk or negedge aresetn)
//     if (~aresetn) ptr_w <= 0;
//     else if (address_accept & ~full) ptr_w <= ptr_w + 1'b1; // commit address phase and incement pointer

// always @(posedge aclk or negedge aresetn)
//     if (~aresetn) ptr_r <= 0;
//     else if (data_accept & wlast & ready_to_accept_data) ptr_r <= ptr_r + 1'b1; // commit last data in transaction and increment pointer

// always @(posedge aclk or negedge aresetn)
//     if (~aresetn) queue <= 0;
//     else if (address_accept & ~full) queue[ptr_w[queue_depth-1:0]] <= 1'b1; // commit address phase
//     else if (data_accept & wlast & nempty) queue[ptr_r[queue_depth-1:0]] <= 1'b0; // commit last data in transaction

