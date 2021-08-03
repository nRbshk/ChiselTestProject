module Axi4_3_data_address_synchronous(
  input   io_clk,
  input   io_rstn,
  input   io_wvalid,
  input   io_wready,
  input   io_wlast,
  input   io_awvalid,
  input   io_awready,
  output  io_wvalid_o,
  output  io_wready_o,
  output  io_wlast_o,
  output  io_awvalid_o,
  output  io_awready_o
);
  wire  queue_io_clk; // @[Axi4_3_data_address_synchronous.scala 43:21]
  wire  queue_io_rstn; // @[Axi4_3_data_address_synchronous.scala 43:21]
  wire  queue_io_ren; // @[Axi4_3_data_address_synchronous.scala 43:21]
  wire  queue_io_wen; // @[Axi4_3_data_address_synchronous.scala 43:21]
  wire  queue_io_full; // @[Axi4_3_data_address_synchronous.scala 43:21]
  wire  queue_io_nempty; // @[Axi4_3_data_address_synchronous.scala 43:21]
  wire  data_accept = io_wvalid & io_wready; // @[Axi4_3_data_address_synchronous.scala 32:28]
  wire  output_address_accept = io_awready_o & io_awvalid_o; // @[Axi4_3_data_address_synchronous.scala 36:41]
  wire  nempty = queue_io_nempty; // @[Axi4_3_data_address_synchronous.scala 46:20 Axi4_3_data_address_synchronous.scala 53:10]
  wire  full = queue_io_full; // @[Axi4_3_data_address_synchronous.scala 45:18 Axi4_3_data_address_synchronous.scala 52:8]
  wire  _io_awready_o_T = ~full; // @[Axi4_3_data_address_synchronous.scala 60:19]
  Queue_Model queue ( // @[Axi4_3_data_address_synchronous.scala 43:21]
    .io_clk(queue_io_clk),
    .io_rstn(queue_io_rstn),
    .io_ren(queue_io_ren),
    .io_wen(queue_io_wen),
    .io_full(queue_io_full),
    .io_nempty(queue_io_nempty)
  );
  assign io_wvalid_o = nempty & io_wvalid; // @[Axi4_3_data_address_synchronous.scala 56:25]
  assign io_wready_o = nempty & io_wready; // @[Axi4_3_data_address_synchronous.scala 57:25]
  assign io_wlast_o = nempty & io_wlast; // @[Axi4_3_data_address_synchronous.scala 58:24]
  assign io_awvalid_o = _io_awready_o_T & io_awvalid; // @[Axi4_3_data_address_synchronous.scala 61:25]
  assign io_awready_o = ~full & io_awready; // @[Axi4_3_data_address_synchronous.scala 60:25]
  assign queue_io_clk = io_clk; // @[Axi4_3_data_address_synchronous.scala 48:16]
  assign queue_io_rstn = io_rstn; // @[Axi4_3_data_address_synchronous.scala 49:11]
  assign queue_io_ren = data_accept & io_wlast & ~output_address_accept; // @[Axi4_3_data_address_synchronous.scala 40:34]
  assign queue_io_wen = io_awvalid & io_awready; // @[Axi4_3_data_address_synchronous.scala 33:32]
endmodule
