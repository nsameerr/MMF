USE [MMFIDB]
GO

/****** Object:  Table [dbo].[mmf_investor_registration_data_tb]    Script Date: 01/01/2016 15:58:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[mmf_investor_registration_data_tb](
	[id] [numeric](11, 0) NOT NULL,
	[registration_id] [varchar](20) NULL,
	[email] [varchar](50) NULL,
	[firstname] [varchar](50) NULL,
	[middlename] [varchar](50) NULL,
	[lastname] [varchar](50) NULL,
	[fatherSpouse] [varchar](50) NULL,
	[dob] [datetime] NULL,
	[nationality] [varchar](50) NULL,
	[status] [varchar](50) NULL,
	[gender] [varchar](50) NULL,
	[mstatus] [varchar](50) NULL,
	[uid] [varchar](20) NULL,
	[pan] [varchar](20) NULL,
	[caddress] [varchar](100) NULL,
	[cpincode] [varchar](10) NULL,
	[country] [varchar](50) NULL,
	[cstate] [varchar](50) NULL,
	[ccity] [varchar](50) NULL,
	[cproof] [varchar](100) NULL,
	[cvalidity] [datetime] NULL,
	[permenentAddress] [bit] NULL,
	[pAddress] [varchar](100) NULL,
	[ppin] [varchar](10) NULL,
	[pcountry] [varchar](50) NULL,
	[pstate] [varchar](50) NULL,
	[pcity] [varchar](50) NULL,
	[pproof] [varchar](100) NULL,
	[pvalidity] [datetime] NULL,
	[mobile] [varchar](15) NULL,
	[htelephone] [varchar](20) NULL,
	[rtelephone] [varchar](20) NULL,
	[ftelphone] [varchar](20) NULL,
	[bankname] [varchar](100) NULL,
	[accountType] [varchar](100) NULL,
	[accno] [varchar](35) NULL,
	[bifsc] [varchar](20) NULL,
	[bmicr] [varchar](15) NULL,
	[baddress] [varchar](100) NULL,
	[bpincode] [varchar](10) NULL,
	[bcountry] [varchar](50) NULL,
	[bstate] [varchar](50) NULL,
	[bcity] [varchar](50) NULL,
	[openAccountType] [varchar](50) NULL,
	[dp] [varchar](30) NULL,
	[tradingtAccount] [varchar](50) NULL,
	[dematAccount] [varchar](50) NULL,
	[smsFacility] [varchar](10) NULL,
	[fstHldrOccup] [varchar](50) NULL,
	[fstHldrOrg] [varchar](50) NULL,
	[fstHldrDesig] [varchar](50) NULL,
	[fstHldrIncome] [varchar](35) NULL,
	[fstHldrNet] [datetime] NULL,
	[fstHldrAmt] [varchar](15) NULL,
	[pep] [varchar](10) NULL,
	[rpep] [varchar](10) NULL,
	[scndHldrName] [varchar](50) NULL,
	[scndHldrOccup] [varchar](50) NULL,
	[scndHldrOrg] [varchar](50) NULL,
	[scndHldrDesig] [varchar](50) NULL,
	[scndHldrSms] [varchar](10) NULL,
	[scndHldrIncome] [varchar](35) NULL,
	[scndHldrNet] [datetime] NULL,
	[scndHldrAmt] [varchar](10) NULL,
	[scndPep] [varchar](10) NULL,
	[scndRpep] [varchar](10) NULL,
	[instrn1] [varchar](10) NULL,
	[instrn2] [varchar](10) NULL,
	[instrn3] [varchar](10) NULL,
	[instrn4] [varchar](10) NULL,
	[instrn5] [varchar](50) NULL,
	[depoPartcpnt] [varchar](50) NULL,
	[deponame] [varchar](50) NULL,
	[beneficiary] [varchar](150) NULL,
	[docEvdnc] [varchar](50) NULL,
	[other] [varchar](50) NULL,
	[experience] [varchar](5) NULL,
	[contractNote] [varchar](45) NULL,
	[intrntTrading] [varchar](10) NULL,
	[alert] [varchar](30) NULL,
	[relationship] [varchar](50) NULL,
	[panAddtnl] [varchar](15) NULL,
	[otherInformation] [varchar](50) NULL,
	[nomineeExist] [bit] NULL,
	[nameNominee] [varchar](50) NULL,
	[nomineeRelation] [varchar](50) NULL,
	[nomineeDob] [datetime] NULL,
	[nominePan] [varchar](15) NULL,
	[nomineeAddress] [varchar](100) NULL,
	[nomineePincode] [varchar](10) NULL,
	[nomCountry] [varchar](50) NULL,
	[nomState] [varchar](50) NULL,
	[nomCity] [varchar](50) NULL,
	[notelephone] [varchar](20) NULL,
	[nRtelephone] [varchar](20) NULL,
	[nomineeFax] [varchar](20) NULL,
	[nomMobile] [varchar](15) NULL,
	[nomEmail] [varchar](50) NULL,
	[minorExist] [bit] NULL,
	[minorGuard] [varchar](50) NULL,
	[mnrReltn] [varchar](50) NULL,
	[mnrDob] [datetime] NULL,
	[minorAddress] [varchar](100) NULL,
	[mnrCountry] [varchar](50) NULL,
	[mnrState] [varchar](50) NULL,
	[mnrCity] [varchar](50) NULL,
	[mnrPincode] [varchar](10) NULL,
	[minortel] [varchar](20) NULL,
	[minrRestel] [varchar](20) NULL,
	[minorfax] [varchar](20) NULL,
	[mnrMob] [varchar](15) NULL,
	[mnrEmail] [varchar](50) NULL,
	[panFilePath] [varchar](100) NULL,
	[coresFilePath] [varchar](100) NULL,
	[prmntFilePath] [varchar](100) NULL,
	[docFilePath] [varchar](100) NULL,
	[usNational] [varchar](10) NULL,
	[usResident] [varchar](10) NULL,
	[usBorn] [varchar](10) NULL,
	[usAddress] [varchar](10) NULL,
	[usTelephone] [varchar](10) NULL,
	[usStandingInstruction] [varchar](10) NULL,
	[usPoa] [varchar](10) NULL,
	[usMailAddress] [varchar](10) NULL,
	[individualTaxIdntfcnNmbr] [varchar](15) NULL,
	[secondHldrPan] [varchar](15) NULL,
	[secondHldrDependentRelation] [varchar](50) NULL,
	[secondHldrDependentUsed] [varchar](50) NULL,
	[firstHldrDependentUsed] [varchar](50) NULL,
	[kitNumber] [varchar](20) NULL,
	[nomineeProof] [varchar](50) NULL,
	[nomineeAadhar] [varchar](20) NULL,
	[nomineeMinorProof] [varchar](50) NULL,
	[nomineeMinorPan] [varchar](20) NULL,
	[nomineeMinorAadhar] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


