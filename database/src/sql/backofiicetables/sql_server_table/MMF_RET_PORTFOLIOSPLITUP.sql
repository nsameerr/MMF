USE [MMFIDB]
GO

/****** Object:  Table [dbo].[MMF_Ret_PortFolioSplitup]    Script Date: 01/01/2016 16:00:05 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MMF_Ret_PortFolioSplitup](
	[Id] [int] NOT NULL,
	[Clientid] [int] NULL,
	[Isin] [varchar](20) NULL,
	[DpHolding] [numeric](15, 3) NOT NULL,
	[NsePool] [numeric](15, 3) NOT NULL,
	[BsePool] [numeric](15, 3) NOT NULL,
	[NseUnAc] [numeric](15, 3) NOT NULL,
	[BseUnAc] [numeric](15, 3) NOT NULL,
	[NsePend] [numeric](15, 3) NOT NULL,
	[BsePend] [numeric](15, 3) NOT NULL,
	[NseFinalQty] [numeric](15, 3) NOT NULL,
	[BseFinalQty] [numeric](15, 3) NOT NULL,
	[AvgRate] [numeric](15, 3) NOT NULL,
	[NseSymbol] [varchar](20) NULL,
	[NseSeries] [varchar](4) NULL,
	[BseSymbol] [varchar](20) NULL,
	[BseSeries] [varchar](4) NULL,
	[Bsecode] [varchar](20) NULL,
	[TradeCode] [varchar](20) NULL,
	[ItClient] [varchar](1) NULL,
	[Location] [varchar](20) NULL,
	[Finance_stock] [int] NOT NULL,
	[Finance_AvgCost] [numeric](15, 3) NOT NULL,
	[PledgeQty] [numeric](15, 3) NOT NULL,
	[Mtf_DP_Qty] [numeric](15, 3) NOT NULL,
	[Weighted_AverageRate] [numeric](15, 4) NOT NULL,
	[Finance_AvgRate] [numeric](15, 3) NOT NULL,
	[Multiplier] [numeric](15, 3) NOT NULL,
	[McxPool] [numeric](15, 3) NOT NULL,
	[McxUnAc] [numeric](15, 3) NOT NULL,
	[McxPend] [numeric](15, 3) NOT NULL,
	[McxFinalQty] [numeric](15, 3) NOT NULL,
	[McxSymbol] [varchar](20) NULL,
	[McxSeries] [varchar](4) NULL,
	[IssueDate] [varchar](16) NULL,
	[ExpiryDate] [datetime] NULL,
	[NseToken] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[MMF_Ret_PortFolioSplitup] ADD  DEFAULT ((0)) FOR [DpHolding]
GO

ALTER TABLE [dbo].[MMF_Ret_PortFolioSplitup] ADD  DEFAULT ((0)) FOR [NsePool]
GO

ALTER TABLE [dbo].[MMF_Ret_PortFolioSplitup] ADD  DEFAULT ((0)) FOR [BsePool]
GO

ALTER TABLE [dbo].[MMF_Ret_PortFolioSplitup] ADD  DEFAULT ((0)) FOR [NseUnAc]
GO

ALTER TABLE [dbo].[MMF_Ret_PortFolioSplitup] ADD  DEFAULT ((0)) FOR [BseUnAc]
GO

ALTER TABLE [dbo].[MMF_Ret_PortFolioSplitup] ADD  DEFAULT ((0)) FOR [NsePend]
GO

ALTER TABLE [dbo].[MMF_Ret_PortFolioSplitup] ADD  DEFAULT ((0)) FOR [BsePend]
GO


