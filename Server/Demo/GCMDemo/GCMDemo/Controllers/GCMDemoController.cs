using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using GCMDemo.Models;
using System.Text;
using System.IO;

namespace GCMDemo.Controllers
{
    public class GCMDemoController : ApiController
    {
        GCMDemoDatabaseDataContext db = new GCMDemoDatabaseDataContext();
        [HttpPost]
        [ActionName("insertRegID")]
        public bool insertRegID(string regId)
        {
            try
            {
               // GCMDemoDatabaseDataContext db = new GCMDemoDatabaseDataContext();
                GCMRegistration reg = db.GCMRegistrations.FirstOrDefault(c => c.REgID == regId);
                if (reg == null)
                {
                    reg = new GCMRegistration();
                    reg.REgID = regId;
                    reg.IsDelete = 0;
                    db.GCMRegistrations.InsertOnSubmit(reg);
                }
                reg.DateCreate = DateTime.Now;
                db.SubmitChanges();
                return true;
            }
            catch (Exception)
            {

            }
            return false;
        }
        [HttpGet]
        [ActionName("tesApi")]
        public int tesApi(){
            return 10;
        }
        public GCMRegistration[] getListRegIDs(int isDeleted)
        {
            var listc = db.GCMRegistrations.Where(x => x.IsDelete == isDeleted);
            return listc.ToArray();
        }
        [HttpPost]
        [ActionName("insertRegID")]
        private String receiverGCM(String regID, String title, String receiverRegID) {

            try
            {

                //đây chính là API Key: (copy paste từ Google developer nhé)
                string applicationID = "AIzaSyD-uT6Nf-3AAo-TIHimhToVk-y3VXabw3w";
                //lấy danh sách Registration Id
                string[] arrRegid = db.GCMRegistrations.Where(c => c.REgID == receiverRegID).Select(c => c.REgID).ToArray();

                //đây chính là Sender ID: (copy paste từ Google developer nhé)
                string SENDER_ID = "59839001107";
                //lấy nội dung thông báo
                WebRequest tRequest;
                //thiết lập GCM send
                tRequest = WebRequest.Create("https://android.googleapis.com/gcm/send");
                tRequest.Method = "POST";
                tRequest.UseDefaultCredentials = true;

                tRequest.PreAuthenticate = true;

                tRequest.Credentials = CredentialCache.DefaultNetworkCredentials;

                //định dạng JSON
                tRequest.ContentType = "application/json";
                //tRequest.ContentType = " application/x-www-form-urlencoded;charset=UTF-8";
                tRequest.Headers.Add(string.Format("Authorization: key={0}", applicationID));

                tRequest.Headers.Add(string.Format("Sender: id={0}", SENDER_ID));

                string RegArr = string.Empty;

                RegArr = string.Join("\",\"", arrRegid);
                //Post Data có định dạng JSON như sau:
                /*
                *  { "collapse_key": "score_update",     "time_to_live": 108,       "delay_while_idle": true,
                "data": {
                "score": "223/3",
                "time": "14:13.2252"
                },
                "registration_ids":["dh4dhdfh", "dfhjj8", "gjgj", "fdhfdjgfj", "đfjdfj25", "dhdfdj38"]
                }
                */
                string postData = "{ \"registration_ids\": [ \"" + RegArr + "\" ],\"data\": {\"message\": \"" + title + "\",\"collapse_key\":\"" + title + "\"}}";

                Console.WriteLine(postData);
                Byte[] byteArray = Encoding.UTF8.GetBytes(postData);
                tRequest.ContentLength = byteArray.Length;

                Stream dataStream = tRequest.GetRequestStream();
                dataStream.Write(byteArray, 0, byteArray.Length);
                dataStream.Close();

                WebResponse tResponse = tRequest.GetResponse();

                dataStream = tResponse.GetResponseStream();

                StreamReader tReader = new StreamReader(dataStream);

                String sResponseFromServer = tReader.ReadToEnd();

               // txtKetQua.Text = sResponseFromServer; //Lấy thông báo kết quả từ GCM server.
                tReader.Close();
                dataStream.Close();
                tResponse.Close();
                return sResponseFromServer;
               // Response.Write(@"<script language='javascript'>alert('Tui đã nói bạn đừng test mà....\nduythanhcse@gmail.com')</script>");
            }
            catch (Exception ex)
            {
                //Response.Write(ex.ToString());
                string msgError = ex.ToString();
               // Response.Write(@"<script language='javascript'>alert('" + msgError + "')</script>");
                return "Push loi";
            }
        }
    }
}