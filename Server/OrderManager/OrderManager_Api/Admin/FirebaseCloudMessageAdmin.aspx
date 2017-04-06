<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="FirebaseCloudMessageAdmin.aspx.cs" Inherits="OrderManager_Api.Admin.FirebaseCloudMessageAdmin" %>
 
<!DOCTYPE html>
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<title></title>
</head>
<body>
 
<form id="form1" runat="server">
 
<div>
Nhập compcode:
<asp:TextBox ID="txtCompcode" runat="server" Width="326px"></asp:TextBox> 
Nhập UserName:
<asp:TextBox ID="txtUserName" runat="server" Width="326px"></asp:TextBox> 
Nhập tiêu đề muốn gửi thông báo:
<asp:TextBox ID="txtTieuDe" runat="server" Width="326px"></asp:TextBox>
 
Nhập nội dung muốn gửi thông báo:
<asp:TextBox ID="txtNoiDung" runat="server" Height="150px" TextMode="MultiLine" Width="358px"></asp:TextBox>
 
<asp:Button ID="btnGui" runat="server" OnClick="btnGui_Click" Text="Gửi thông báo tới toàn bộ khách hàng" />
 
kết quả sau khi gửi thông báo:
<asp:TextBox ID="txtKetQua" runat="server" Height="150px" TextMode="MultiLine" Width="358px"></asp:TextBox>
 
</div>
 
</form>
 
</body>
</html>