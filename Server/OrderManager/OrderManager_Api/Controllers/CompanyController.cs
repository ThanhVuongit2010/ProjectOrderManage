using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OrderManager_Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Script.Serialization;

namespace OrderManager_Api.Controllers
{
    public class CompanyController : ApiController
    {
        OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        // Insert or update Company
        //[HttpPost]
        //public JToken CompanyInsert([FromBody] JObject companys)
        //{
        //    var insertRusult = 0;
        //    OD_COMPANY objCompany = new OD_COMPANY();
        //    JToken company = companys["company"];
        //    objCompany.Comp_Code = (String)company["Comp_Code"];
        //    objCompany.Comp_Name = (String)company["Comp_Name"];
        //    objCompany.Group_Comp_code = (String)company["Group_Comp_code"];
        //    objCompany.Email = (String)company["Email"];
        //    objCompany.Address = (String)company["Address"];
        //    objCompany.Picture = (String)company["Picture"];
        //    String phone = (String)company["Phone"];
        //    if (String.IsNullOrEmpty(phone)) {
        //        objCompany.Phone = Convert.ToInt64(Math.Round(Convert.ToDouble(phone)));
        //    }
        //    objCompany.Description = (String)company["Description"];
        //    String numberMonth = (String)company["UsingTime"];
        //    DateTime now = DateTime.Now;
        //    objCompany.Apply_Start_Date = now;
        //    DateTime endDate = now.AddMonths(Int32.Parse(numberMonth));
        //    objCompany.Apply_End_Date = endDate;

        //    var listCompany = db.OD_COMPANies.Where(i => i.Comp_Code == objCompany.Comp_Code).ToList();
        //    if (listCompany.Count > 0)
        //    {
        //        try
        //        {
        //            listCompany[0].Comp_Code = objCompany.Comp_Code;
        //            listCompany[0].Comp_Name = objCompany.Comp_Name;
        //            listCompany[0].Group_Comp_code = objCompany.Group_Comp_code;
        //            listCompany[0].Email = objCompany.Email;
        //            listCompany[0].Picture = objCompany.Picture;
        //            listCompany[0].Address = objCompany.Address;
        //            listCompany[0].Phone = objCompany.Phone;
        //            listCompany[0].Description = objCompany.Description;
        //            listCompany[0].Apply_Start_Date = objCompany.Apply_Start_Date;
        //            listCompany[0].Apply_End_Date = objCompany.Apply_End_Date;

        //            db.SubmitChanges();
        //            insertRusult = 1;
        //        }
        //        catch (Exception ex)
        //        {
        //            return JObject.FromObject(new { type = 0, data = ex });
        //        }
        //    }
        //    else
        //    {
        //        try
        //        {
        //            db.OD_COMPANies.InsertOnSubmit(objCompany);
        //            db.SubmitChanges();
        //            insertRusult = 1;
        //        }
        //        catch (Exception ex)
        //        {

        //            return JObject.FromObject(new { type = 0, data = ex });
        //        }

        //    }
        //    return JObject.FromObject(new { type = 0, data = insertRusult });
        //}
        //// Delete mutilple record Company
        //[HttpPost]
        //public JToken CompanyDelete([FromBody] String compcodeList)
        //{
        //    int deleteResult = 0;
        //    String[] arrayCompcode = compcodeList.Split(',');
        //    var companyList = db.OD_COMPANies.Where(i => arrayCompcode.Contains(i.Comp_Code)).ToList();
        //    try
        //    {
        //        foreach (var company in companyList) {
        //            db.OD_COMPANies.DeleteOnSubmit(company);
        //        }
        //        db.SubmitChanges();
        //        deleteResult = 1;

        //    }
        //    catch (Exception ex)
        //    {
        //        return JObject.FromObject(new { type = 0, data = ex });
        //    }
        //    return JObject.FromObject(new { type = 0, data = deleteResult });
        //}
        //// get List Company by CompCode
        //[HttpPost]
        //public JToken getCompanyByCompCode([FromBody]String compcodeList)
        //{
        //    String[] arrayCompcode = compcodeList.Split(',');
        //    var dt = db.OD_COMPANies.Where(i => arrayCompcode.Contains(i.Comp_Code)).ToList();
        //    if (dt == null)
        //    {
        //        return JObject.FromObject(new { type = 0, data = 0 });
        //    }
        //    return JObject.FromObject(new { type = 0, data = dt });
        //}
        //[HttpPost]
        //public String getValue() {
        //    return "Test";
        //}
       
    }
   
}