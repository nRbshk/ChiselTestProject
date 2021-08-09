package apb

import chisel3._
import chisel3.util._





class APB_slave(AW: Int = 8, DW: Int = 32) extends RawModule {
    val io = IO(new Bundle {
        val in = Input(new APB_interface_master(AW, DW))
        val out = Output(new APB_interface_slave(DW))
    })

    val write = Wire(Bool())
    write := io.in.PSEL && io.in.PWRITE && ~io.in.PENABLE
    val write_enable = Wire(Bool())
    write_enable := io.in.PSEL && io.in.PWRITE && io.in.PENABLE

    val read = Wire(Bool())
    read := io.in.PSEL && ~io.in.PWRITE && ~io.in.PENABLE
    val read_enable = Wire(Bool()) 
    read_enable := io.in.PSEL && ~io.in.PWRITE && io.in.PENABLE


    withClockAndReset(clock=io.in.PCLK, reset=io.in.PRESETn) {
        val state = RegInit(State.IDLE)

        switch (state) {
            is (State.IDLE){
                when (write) { state := State.WR }
                .elsewhen (read) { state := State.RD }
                .otherwise { state := State.IDLE }
            }
            is (State.WR) {
                when (write_enable) { state := State.IDLE }
                .otherwise { state := State.WR }
            }
            is (State.RD) {
                when (read_enable) { state := State.IDLE }
                .otherwise { state := State.RD }
            }
        }

    }


}