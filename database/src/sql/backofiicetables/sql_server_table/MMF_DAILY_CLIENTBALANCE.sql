USE [MMFIDB]
GO

/****** Object:  Table [dbo].[MMF_Daily_ClientBalance]    Script Date: 01/01/2016 15:54:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MMF_Daily_ClientBalance](
	[SlNo] [int] IDENTITY(1,1) NOT NULL,
	[Trandate] [datetime] NOT NULL,
	[Clientid] [int] NOT NULL,
	[TradeCode] [varchar](16) NOT NULL,
	[LedgerBalance] [numeric](15, 2) NOT NULL,
	[Euser] [varchar](16) NOT NULL,
	[LastUpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_MMF_Daily_ClientBalance] PRIMARY KEY CLUSTERED 
(
	[Trandate] ASC,
	[TradeCode] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[MMF_Daily_ClientBalance] ADD  CONSTRAINT [DF_MMF_Daily_ClientBalance_LedgerBalance]  DEFAULT ((0)) FOR [LedgerBalance]
GO

ALTER TABLE [dbo].[MMF_Daily_ClientBalance] ADD  CONSTRAINT [DF_MMF_Daily_ClientBalance_LastUpdatedOn]  DEFAULT (getdate()) FOR [LastUpdatedOn]
GO


