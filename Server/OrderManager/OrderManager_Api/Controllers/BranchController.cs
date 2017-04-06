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
    public class BranchController : ApiController
    {

        //OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        //// Insert or update Branch
        //[HttpPost]
        //public JToken BranchInsert([FromBody] JObject branchs)
        //{
        //    var insertRusult = 0;
        //    OD_BRANCH objbranch = new OD_BRANCH();

        //    JToken branch = branchs["branch"];
        //    objbranch.Branch_Code = (String)branch["Branch_Code"];
        //    objbranch.Branch_Name = (String)branch["Branch_Name"];
        //    objbranch.Comp_code = (String)branch["Comp_code"];
        //    String type = (String)branch["Type"];
        //    if (String.IsNullOrEmpty(type)) {
        //        objbranch.Type = Int32.Parse(type);
        //    }
        //    objbranch.Picture = (String)branch["Picture"];
        //    String phone = (String)branch["Phone"];
        //    if (String.IsNullOrEmpty(phone))
        //    {
        //        objbranch.Type = Int32.Parse(phone);
        //    }
        //    objbranch.Description = (String)branch["Description"];
        //    String numberMonth = (String)branch["UsingTime"];
        //    DateTime now = DateTime.Now;
        //    objbranch.Apply_Start_Date = now;
        //    DateTime endDate = now.AddMonths(Int32.Parse(numberMonth));
        //    objbranch.Apply_End_Date = endDate;

        //    var branchList = db.OD_BRANCHes.Where(i => i.Branch_Code == objbranch.Branch_Code).ToList();
        //    if (branchList.Count > 0)
        //    {
        //        try
        //        {
        //            branchList[0].Branch_Code = objbranch.Branch_Code;
        //            branchList[0].Branch_Name = objbranch.Branch_Name;
        //            branchList[0].Comp_code = objbranch.Comp_code;
        //            branchList[0].Type = objbranch.Type;
        //            branchList[0].Picture = objbranch.Picture;
        //            branchList[0].Phone = objbranch.Phone;
        //            branchList[0].Description = objbranch.Description;
        //            branchList[0].Apply_Start_Date = objbranch.Apply_Start_Date;
        //            branchList[0].Apply_Start_Date = objbranch.Apply_Start_Date;

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
        //            db.OD_BRANCHes.InsertOnSubmit(objbranch);
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
        //// Delete mutilple record Branch
        //[HttpPost]
        //public JToken BranchDelete([FromBody] String branchCodeList)
        //{
        //    int deleteResult = 0;
        //    String[] arrayBranchCode = branchCodeList.Split(',');
        //    var branchList = db.OD_BRANCHes.Where(i => arrayBranchCode.Contains(i.Comp_code)).ToList();
        //    try
        //    {
        //        foreach (var branch in branchList)
        //        {
        //            db.OD_BRANCHes.DeleteOnSubmit(branch);
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
        //// get List Company by Branch
        //[HttpPost]
        //public JToken getBranchByBranchCode([FromBody]String BranchCodeList)
        //{
        //    String[] arrayBranchCode = BranchCodeList.Split(',');
        //    var dt = db.OD_BRANCHes.Where(i => arrayBranchCode.Contains(i.Branch_Code)).ToList();
        //    if (dt == null)
        //    {
        //        return JObject.FromObject(new { type = 0, data = 0 });
        //    }
        //    return JObject.FromObject(new { type = 0, data = dt });
        //}
    }
}