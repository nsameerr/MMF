USE [MMFIDB]
GO

/****** Object:  Table [dbo].[MMF_JobSchedule_Details]    Script Date: 01/01/2016 15:59:35 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MMF_JobSchedule_Details](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[JobType] [varchar](10) NOT NULL,
	[Status] [varchar](20) NOT NULL,
	[ScheduledDate] [datetime] NOT NULL,
	[MMFTransferDate] [datetime] NULL,
	[LastUpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_MMF_JobSchedule_Details] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[MMF_JobSchedule_Details] ADD  CONSTRAINT [DF_MMF_JobSchedule_Details_LastUpdatedOn]  DEFAULT (getdate()) FOR [LastUpdatedOn]
GO


