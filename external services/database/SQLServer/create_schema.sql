IF NOT EXISTS(SELECT * FROM sys.databases WHERE name='spring_demo')
BEGIN
  CREATE DATABASE spring_demo;
END
GO

USE spring_demo;
GO


USE [spring_demo]
GO

/****** Object:  Table [dbo].[resource_1]    Script Date: 10-05-2023 21:06:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[resource_1](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[emb_field1] [bit] NULL,
	[emb_field2] [numeric](38, 0) NULL,
	[created_at] [datetime2](6) NOT NULL DEFAULT GETDATE(),
	[updated_at] [datetime2](6) NOT NULL DEFAULT GETDATE(),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[resource1_collections]    Script Date: 10-05-2023 21:07:57 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[resource1_collections](
	[resource1_id] [bigint] NOT NULL,
	[collections] [varchar](5000) NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[resource1_collections]  WITH CHECK ADD  CONSTRAINT [FK_resource_1] FOREIGN KEY([resource1_id])
REFERENCES [dbo].[resource_1] ([id])
GO

ALTER TABLE [dbo].[resource1_collections] CHECK CONSTRAINT [FK_resource_1]
GO

