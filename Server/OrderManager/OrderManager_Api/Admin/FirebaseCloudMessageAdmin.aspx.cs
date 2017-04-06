using OrderManager_Api.Common;
using OrderManager_Api.Controllers;
using OrderManager_Api.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace OrderManager_Api.Admin
{
    public partial class FirebaseCloudMessageAdmin : System.Web.UI.Page
    {
        OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        protected void Page_Load(object sender, EventArgs e)
        {

        }
        protected void btnGui_Click(object sender, EventArgs e)
        {
            FCMController fcmController = new FCMController();
            List<OD_USER> dsFcm = fcmController.TestPustFromSerVer(txtCompcode.Text, txtUserName.Text);
            WebRequest tRequest;
            //thiết lập FCM send
            tRequest = WebRequest.Create(Constant.severFireCloudMessage);
            tRequest.Method = "POST";
            tRequest.UseDefaultCredentials = true;

            tRequest.PreAuthenticate = true;

            tRequest.Credentials = CredentialCache.DefaultNetworkCredentials;

            //định dạng JSON
            tRequest.ContentType = "application/json";
            tRequest.Headers.Add(string.Format("Authorization: key={0}", Constant.serverkey));
            tRequest.Headers.Add(string.Format("Sender: id={0}", Constant.senderID));

            string[] arrRegid = dsFcm.Select(x => x.Device_Token).ToArray();
            string RegArr = string.Empty;
            RegArr = string.Join("\",\"", arrRegid);

            string postData = "{ \"registration_ids\": [ \"" + RegArr + "\" ],\"data\": {\"message\": \"" + txtNoiDung.Text + "\",\"body\": \"" + txtNoiDung.Text + "\",\"title\": \"" + txtTieuDe.Text + "\",\"collapse_key\":\"" + txtNoiDung.Text + "\"}}";

            Byte[] byteArray = Encoding.UTF8.GetBytes(postData);
            tRequest.ContentLength = byteArray.Length;

            Stream dataStream = tRequest.GetRequestStream();
            dataStream.Write(byteArray, 0, byteArray.Length);
            dataStream.Close();

            WebResponse tResponse = tRequest.GetResponse();

            dataStream = tResponse.GetResponseStream();

            StreamReader tReader = new StreamReader(dataStream);

            String sResponseFromServer = tReader.ReadToEnd();

            txtKetQua.Text = sResponseFromServer; //Lấy thông báo kết quả từ FCM server.
            tReader.Close();
            dataStream.Close();
            tResponse.Close();
        }
    }

}