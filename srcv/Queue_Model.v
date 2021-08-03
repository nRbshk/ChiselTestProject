module Queue_Model(
  input   io_clk,
  input   io_rstn,
  input   io_ren,
  input   io_wen,
  output  io_full,
  output  io_nempty
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
`endif // RANDOMIZE_REG_INIT
  wire  _T_1 = io_wen & ~io_full; // @[Queue.scala 36:22]
  reg [1:0] ptr_w; // @[Reg.scala 27:20]
  reg [3:0] queue; // @[Reg.scala 27:20]
  wire  _T_6 = io_ren & io_nempty; // @[Queue.scala 37:27]
  reg [1:0] ptr_r; // @[Reg.scala 27:20]
  wire  _GEN_8 = 2'h1 == ptr_r ? 1'h0 : queue[1]; // @[Queue.scala 37:120 Queue.scala 37:120 Queue.scala 37:54]
  wire  _GEN_12 = io_ren & io_nempty ? _GEN_8 : queue[1]; // @[Queue.scala 37:41 Queue.scala 38:33]
  wire  queue_next_1 = io_wen & ~io_full ? 2'h1 == ptr_w | queue[1] : _GEN_12; // @[Queue.scala 36:35]
  wire  _GEN_7 = 2'h0 == ptr_r ? 1'h0 : queue[0]; // @[Queue.scala 37:120 Queue.scala 37:120 Queue.scala 37:54]
  wire  _GEN_11 = io_ren & io_nempty ? _GEN_7 : queue[0]; // @[Queue.scala 37:41 Queue.scala 38:33]
  wire  queue_next_0 = io_wen & ~io_full ? 2'h0 == ptr_w | queue[0] : _GEN_11; // @[Queue.scala 36:35]
  wire  _GEN_10 = 2'h3 == ptr_r ? 1'h0 : queue[3]; // @[Queue.scala 37:120 Queue.scala 37:120 Queue.scala 37:54]
  wire  _GEN_14 = io_ren & io_nempty ? _GEN_10 : queue[3]; // @[Queue.scala 37:41 Queue.scala 38:33]
  wire  queue_next_3 = io_wen & ~io_full ? 2'h3 == ptr_w | queue[3] : _GEN_14; // @[Queue.scala 36:35]
  wire  _GEN_9 = 2'h2 == ptr_r ? 1'h0 : queue[2]; // @[Queue.scala 37:120 Queue.scala 37:120 Queue.scala 37:54]
  wire  _GEN_13 = io_ren & io_nempty ? _GEN_9 : queue[2]; // @[Queue.scala 37:41 Queue.scala 38:33]
  wire  queue_next_2 = io_wen & ~io_full ? 2'h2 == ptr_w | queue[2] : _GEN_13; // @[Queue.scala 36:35]
  wire [3:0] _queue_T = {queue_next_3,queue_next_2,queue_next_1,queue_next_0}; // @[Queue.scala 30:47]
  wire  _queue_T_4 = _T_6 | _T_1; // @[Queue.scala 30:109]
  wire [1:0] ptr_r_next = ptr_r + 2'h1; // @[Queue.scala 41:29]
  wire [1:0] ptr_w_next = ptr_w + 2'h1; // @[Queue.scala 42:29]
  assign io_full = &queue; // @[Queue.scala 44:33]
  assign io_nempty = |queue; // @[Queue.scala 45:35]
  always @(posedge io_clk or posedge io_rstn) begin
    if (io_rstn) begin
      ptr_w <= 2'h0;
    end else if (_T_1) begin
      ptr_w <= ptr_w_next;
    end
  end
  always @(posedge io_clk or posedge io_rstn) begin
    if (io_rstn) begin
      queue <= 4'h0;
    end else if (_queue_T_4) begin
      queue <= _queue_T;
    end
  end
  always @(posedge io_clk or posedge io_rstn) begin
    if (io_rstn) begin
      ptr_r <= 2'h0;
    end else if (_T_6) begin
      ptr_r <= ptr_r_next;
    end
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  ptr_w = _RAND_0[1:0];
  _RAND_1 = {1{`RANDOM}};
  queue = _RAND_1[3:0];
  _RAND_2 = {1{`RANDOM}};
  ptr_r = _RAND_2[1:0];
`endif // RANDOMIZE_REG_INIT
  if (io_rstn) begin
    ptr_w = 2'h0;
  end
  if (io_rstn) begin
    queue = 4'h0;
  end
  if (io_rstn) begin
    ptr_r = 2'h0;
  end
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
