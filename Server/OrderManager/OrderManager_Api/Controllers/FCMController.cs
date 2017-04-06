using OrderManager_Api.Common;
using OrderManager_Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OrderManager_Api.Controllers
{
    public class FCMController : ApiController
    {
        private static String placeOrder = null;
        OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        [HttpPost]
        public IHttpActionResult PostOrderPush(String compcode, String username, String title, String message, String isorder) {
           
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
            // get list user need push
            OD_USER userOrder = this.getUserOrder(compcode, username);
            String[] arrTitle = title.Split(',');
            // get vi tri order
            String placeorder = arrTitle[0];
            String titlePush = arrTitle[1];
            if (Constant.ROLE_004.Equals(userOrder.Role_Code) )
            {
                placeOrder = placeorder;
            }
            List<OD_USER> dsFcm = this.getListUserReceivePush(userOrder, isorder);

            string[] arrRegid = dsFcm.Select(x => x.Device_Token).ToArray();
            string RegArr = string.Empty;
            RegArr = string.Join("\",\"", arrRegid);

            int companycode = Convert.ToInt32(Math.Round(Convert.ToDouble(compcode)));
            var dataRow = (from company in db.OD_COMPANies
                           from user in db.OD_USERs
                           where company.Comp_Code == user.Comp_Code && company.Comp_Code == companycode 
                           && user.UserName.Equals(username) 
                           select user).ToList();
            return Ok();
        }
        
        /// <summary>
        /// get infomation user Order
        /// </summary>
        /// <param name="compcode"></param>
        /// <param name="username"></param>
        /// <returns></returns>
        private OD_USER getUserOrder(String compcode, String username)
        {
            int companycode = Convert.ToInt32(Math.Round(Convert.ToDouble(compcode)));
            var dataRow = db.OD_USERs.Where(i => i.Comp_Code == companycode && i.UserName.Equals(username)).ToList();
            if (dataRow != null && dataRow.Count > 0) {
                return dataRow[0];
            }
            return null;
        }
        private List<OD_USER> getListUserReceivePush(OD_USER userOrder, String isorder) {
            if (Constant.ROLE_006.Equals(userOrder.Role_Code))
            {
                var dataRow = db.OD_USERs.Where(i => i.Comp_Code == userOrder.Comp_Code && i.Branch_Code == userOrder.Branch_Code && Constant.ROLE_004.Equals(userOrder.Role_Code)).ToList();
                return dataRow;
            }
            else if (Constant.ROLE_004.Equals(userOrder.Role_Code))
            {
                int isOder = Int32.Parse(isorder);
                if (isOder == 1)
                {
                    var dataRow = db.OD_USERs.Where(i => i.Comp_Code == userOrder.Comp_Code && i.Branch_Code == userOrder.Branch_Code && Constant.ROLE_005.Equals(userOrder.Role_Code) || Constant.ROLE_003.Equals(userOrder.Role_Code)).ToList();
                    return dataRow;
                }
                else
                {
                    var dataRow = db.OD_USERs.Where(i => i.Comp_Code == userOrder.Comp_Code && i.Branch_Code == userOrder.Branch_Code && Constant.ROLE_006.Equals(userOrder.Role_Code)).ToList();
                    return dataRow;
                }
                
            }
            else if (Constant.ROLE_005.Equals(userOrder.Role_Code))
            {
                var dataRow = db.OD_USERs.Where(i => i.Comp_Code == userOrder.Comp_Code && i.Branch_Code == userOrder.Branch_Code && Constant.ROLE_004.Equals(userOrder.Role_Code) || Constant.ROLE_003.Equals(userOrder.Role_Code)).ToList();
                return dataRow;
            }
            else
            {
                return null;
            }
        }
        private String getDataPush(String deviceToken, String rolecode, String title, String message) {

            String postData = null;
            String[] arrTitle = title.Split(',');
            // get vi tri order
            String placeorder = arrTitle[0];
            String titlePush = arrTitle[1];
            if (Constant.ROLE_004.Equals(rolecode)) {
                postData = "{ \"registration_ids\": [ \"" + deviceToken + "\" ],\"data\": {\"message\": \"" + message + "\",\"body\": \"" + message + "\",\"title\": \"" +title+ "\",\"collapse_key\":\"" + message + "\"}}";
            }

            return postData;
        }
        /// <summary>
        /// Test pust
        /// </summary>
        /// <param name="compcode"></param>
        /// <param name="username"></param>
        /// <returns></returns>
        public List<OD_USER> TestPustFromSerVer(String compcode, String username)
        {
            int companycode = Convert.ToInt32(Math.Round(Convert.ToDouble(compcode)));
            var dataRow = db.OD_USERs.Where(i => i.Comp_Code == companycode && i.UserName.Equals(username)).ToList();
            return dataRow;
        }

    }
}