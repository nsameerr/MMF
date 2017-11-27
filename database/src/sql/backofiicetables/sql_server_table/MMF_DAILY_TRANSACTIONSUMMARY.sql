USE [MMFIDB]
GO

/****** Object:  Table [dbo].[MMF_Daily_TransactionSummary]    Script Date: 01/01/2016 15:58:06 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MMF_Daily_TransactionSummary](
	[SlNo] [int] IDENTITY(1,1) NOT NULL,
	[TranDate] [datetime] NOT NULL,
	[TradeCode] [varchar](50) NOT NULL,
	[ClientId] [int] NOT NULL,
	[OrderNo] [varchar](64) NOT NULL,
	[Product] [varchar](32) NOT NULL,
	[Security] [varchar](32) NOT NULL,
	[Instrument] [varchar](32) NULL,
	[Contract] [varchar](32) NULL,
	[BuySell] [char](1) NOT NULL,
	[Quantity] [numeric](15, 2) NOT NULL,
	[Price] [numeric](15, 2) NOT NULL,
	[Units] [numeric](15, 2) NOT NULL,
	[Volume] [numeric](15, 2) NOT NULL,
	[Brokerage] [numeric](15, 2) NOT NULL,
	[OtherCharges] [numeric](15, 2) NULL,
	[Channel] [varchar](32) NULL,
	[venue_script_code] [varchar](32) NULL,
	[Euser] [varchar](16) NOT NULL,
	[lastUpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_MMF_Daily_TransactionSummary] PRIMARY KEY CLUSTERED 
(
	[TranDate] ASC,
	[TradeCode] ASC,
	[OrderNo] ASC,
	[Product] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[MMF_Daily_TransactionSummary] ADD  CONSTRAINT [DF_MMF_Daily_TransactionSummary_Quantity]  DEFAULT ((0)) FOR [Quantity]
GO

ALTER TABLE [dbo].[MMF_Daily_TransactionSummary] ADD  CONSTRAINT [DF_MMF_Daily_TransactionSummary_Price]  DEFAULT ((0)) FOR [Price]
GO

ALTER TABLE [dbo].[MMF_Daily_TransactionSummary] ADD  CONSTRAINT [DF_MMF_Daily_TransactionSummary_Units]  DEFAULT ((0)) FOR [Units]
GO

ALTER TABLE [dbo].[MMF_Daily_TransactionSummary] ADD  CONSTRAINT [DF_MMF_Daily_TransactionSummary_Volume]  DEFAULT ((0)) FOR [Volume]
GO

ALTER TABLE [dbo].[MMF_Daily_TransactionSummary] ADD  CONSTRAINT [DF_MMF_Daily_TransactionSummary_Brokerage]  DEFAULT ((0)) FOR [Brokerage]
GO

ALTER TABLE [dbo].[MMF_Daily_TransactionSummary] ADD  CONSTRAINT [DF_MMF_Daily_TransactionSummary_OtherCharges]  DEFAULT ((0)) FOR [OtherCharges]
GO

ALTER TABLE [dbo].[MMF_Daily_TransactionSummary] ADD  CONSTRAINT [DF_MMF_Daily_TransactionSummary_lastUpdatedOn]  DEFAULT (getdate()) FOR [lastUpdatedOn]
GO


