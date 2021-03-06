USE [MainDatabase]
GO
/****** Object:  Table [dbo].[track]    Script Date: 01/16/2015 16:26:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[track](
	[trackId] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](max) NOT NULL,
	[author] [nvarchar](max) NOT NULL,
	[length] [nvarchar](max) NOT NULL,
	[price] [money] NOT NULL,
	[category] [nvarchar](max) NULL
) ON [PRIMARY]
GO
