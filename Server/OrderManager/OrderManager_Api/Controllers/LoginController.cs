using Newtonsoft.Json.Linq;
using OrderManager_Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OrderManager_Api.Controllers
{
    public class LoginController : ApiController
    {
        OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        // get info login
        [HttpPost]
        public IHttpActionResult PostUserLogin(String compcode, String username, String password, String deviceToken)
        {
            try
            {
                var today = DateTime.Today;
                int companycode = Convert.ToInt32(Math.Round(Convert.ToDouble(compcode)));
                var dataRow = (from company in db.OD_COMPANies
                               from user in db.OD_USERs
                               where company.Comp_Code == user.Comp_Code && company.Comp_Code == companycode && company.Apply_End_Date >= today && today >= company.Apply_Start_Date
                               && user.UserName.Equals(username) && user.Password.Equals(password) && user.Apply_End_Date >= today && today >= user.Apply_Start_Date
                               select user).ToList();

                if (dataRow == null || dataRow.Count == 0)
                {
                    return NotFound();
                }
                else
                {
                    OD_USER user = dataRow[0];
                    user.Device_Token = deviceToken;
                    db.SubmitChanges();
                    return Ok(user);
                }
            }
            catch (Exception)
            {

                return NotFound();
            }
          
        }
    }
}