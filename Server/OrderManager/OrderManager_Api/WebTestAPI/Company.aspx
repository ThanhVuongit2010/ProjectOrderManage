<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Company.aspx.cs" Inherits="OrderManager_Api.Web_Test_API.Company" %>
<script src="../scripts/jquery-3.1.1.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div style ="width: 100%; float: left">
        <div style="width: 40%; float: left">
            Company Code &nbsp<input id="Code" type="text" class="Code" relcol="Code" /><br />
            Company Name &nbsp<input id="Name" type="text" /><br />
            Company Group &nbsp<input id="Group" type="text" /><br />
            Email &nbsp<input id="Email" type="text" /><br />
            Picture &nbsp<input id="Picture" type="text" /><br />
            Address &nbsp<input id="Address" type="text" /><br />
            Description &nbsp<input id="Description" type="text" /><br />
            Phone &nbsp<input id="Phone" type="text" /><br />
            Using Time &nbsp<input id="UsingTime" type="text" /><br />
            <input type="button" value="Insert" onclick="return CompanyInsert()" />
            <input type="button" value="Delete" onclick="return CompanyDelete()" />
        </div>
    
    </div>
    </form>
    <script type="text/javascript">
        function CompanyInsert() {
            $.ajax({
                type: "POST",
                url: "../api/Company/CompanyInsert",
                dataType: "json",
                data: JSON.stringify({

                    company: {

                        Comp_Code: $('#Code').val(),
                        Comp_Name: $('#Name').val(),
                        Group_Comp_code: $('#Group').val(),
                        Email: $('#Email').val(),
                        Picture: $('#Picture').val(),
                        Address: $('#Address').val(),
                        Description: $('#Description').val(),
                        Phone: $('#Phone').val(),
                        UsingTime: $('#UsingTime').val(),
                    }
                }),
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    console.log(data.data);
                    var isOk = data.data;
                    if (isOk) {
                        alert('Insert thành công !');
                    } else {
                        alert('error !');
                    }
                },
                error: function (e) {
                    return false;
                    alert(e);
                }
            });
            return true;
        }
        function Insert() {
            jQuery.ajax({
                URL: "/AdminPanel/News/DeleteNews",
                data: { "newsId": 1 },
                dataType: "json",
                type: "POST",
                success: function (msg) {
                    alert(msg);
                }
            });
        }
        function CompanyDelete() {
            $.ajax({
                type: "POST",
                url: "../api/Company/CompanyDelete",
                dataType: "json",
                data:{ 
                    Comp_Code: $('#Code').val(),
                },
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    console.log(data.data);
                    var isOk = data.data;
                    if (isOk) {
                        alert('Delete thành công !');
                    } else {
                        alert('error !');
                    }
                },
                error: function (e) {
                    return false;
                    alert(e);
                }
            });
            return true;
        }
        
    </script>
</body>
</html>
