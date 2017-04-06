using Newtonsoft.Json.Linq;
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
    public class RegisterController : ApiController
    {
        OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        // Insert or update Company
        [HttpPost]
        public JToken RegisterAcount1(String jsonregister)
        {
            JObject registers = JObject.Parse(jsonregister);
            var insertRusult = 0;
            OD_COMPANY objCompany = new OD_COMPANY();
            JToken company = registers["register"];
            String compcode = (String)company["Comp_Code"];
            if (!String.IsNullOrEmpty(compcode))
            {
                objCompany.Comp_Code = Int32.Parse(compcode);
            }
            objCompany.Comp_Name = (String)company["Comp_Name"];
            objCompany.Email = (String)company["Email"];
            objCompany.Address = (String)company["Address"];
            String phone = (String)company["Phone"];
            if (!String.IsNullOrEmpty(phone))
            {
                objCompany.Phone = Convert.ToInt64(Math.Round(Convert.ToDouble(phone)));
            }
            String numberMonth = (String)company["UsingTime"];
            DateTime now = DateTime.Now;
            objCompany.Apply_Start_Date = now;
            DateTime endDate = now.AddMonths(Int32.Parse(numberMonth));
            objCompany.Apply_End_Date = endDate;

            OD_USER objUser = new OD_USER();
            objUser.Comp_Code = objCompany.Comp_Code;
            objUser.UserName = (String)company["UserName"];
            objUser.Password =(String)company["Password"];
            objUser.Apply_Start_Date = now;
            objUser.Apply_End_Date = endDate;
            try
            {
                db.OD_COMPANies.InsertOnSubmit(objCompany);
                db.OD_USERs.InsertOnSubmit(objUser);
                db.SubmitChanges();
                insertRusult = 1;
            }
            catch (Exception ex)
            {

                return JObject.FromObject(new { type = 0, data = ex });
            }
            return JObject.FromObject(new { type = 0, data = insertRusult });
        }

        [HttpPost]
        public HttpResponseMessage PostRegisterAcount(String compcode, String compname, String email, String address, String phone, String usingtime, String username, String password)
        {
            OD_COMPANY objCompany = new OD_COMPANY();
            if (!String.IsNullOrEmpty(compcode))
            {
                objCompany.Comp_Code = Int32.Parse(compcode);
            }
            objCompany.Comp_Name = compname;
            objCompany.Email = email;
            objCompany.Address = address;
            if (!String.IsNullOrEmpty(phone))
            {
                objCompany.Phone = Convert.ToInt64(Math.Round(Convert.ToDouble(phone)));
            }
            String numberMonth = usingtime;
            DateTime now = DateTime.Now;
            objCompany.Apply_Start_Date = now;
            DateTime endDate = now.AddMonths(Int32.Parse(numberMonth));
            objCompany.Apply_End_Date = endDate;

            OD_USER objUser = new OD_USER();
            objUser.Comp_Code = objCompany.Comp_Code;
            objUser.UserName = username;
            objUser.Password = password;
            objUser.Role_Code = Constant.ROLE_002;
            objUser.Apply_Start_Date = now;
            objUser.Apply_End_Date = endDate;
            try
            {
                db.OD_COMPANies.InsertOnSubmit(objCompany);
                db.OD_USERs.InsertOnSubmit(objUser);
                db.SubmitChanges();
                return Request.CreateResponse(HttpStatusCode.OK, objUser);
            }
            catch (Exception ex)
            {

                return Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable, ex);
            }
        }
       
    }
}