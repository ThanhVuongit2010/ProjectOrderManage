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
    public class CategoryController : ApiController
    {
        //OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        //// Insert or update Category
        //[HttpPost]
        //public JToken CategoryInsert([FromBody] JObject categorys)
        //{
        //    var insertRusult = 0;
        //    OD_CATEGORY objCategory = new OD_CATEGORY();

        //    JToken category = categorys["category"];
        //    objCategory.Category_Code = (String)category["Category_Code"];
        //    objCategory.Category_Name = (String)category["Category_Name"];
        //    objCategory.Category_Name_EN = (String)category["Category_Name_EN"];
        //    String sort = (String)category["Sort"];
        //    if (String.IsNullOrEmpty(sort))
        //    {
        //        objCategory.Sort = Int32.Parse(sort);
        //    }
        //    objCategory.Picture = (String)category["Picture"];
        //    String numberMonth = (String)category["UsingTime"];
        //    DateTime now = DateTime.Now;
        //    objCategory.Apply_Start_Date = now;
        //    DateTime endDate = now.AddMonths(Int32.Parse(numberMonth));
        //    objCategory.Apply_End_Date = endDate;

        //    var categoryList = db.OD_CATEGORies.Where(i => i.Category_Code == objCategory.Category_Code).ToList();
        //    if (categoryList.Count > 0)
        //    {
        //        try
        //        {
        //            categoryList[0].Category_Code = objCategory.Category_Code;
        //            categoryList[0].Category_Name = objCategory.Category_Name;
        //            categoryList[0].Category_Name_EN = objCategory.Category_Name_EN;
        //            categoryList[0].Sort = objCategory.Sort;
        //            categoryList[0].Picture = objCategory.Picture;
        //            categoryList[0].Apply_Start_Date = objCategory.Apply_Start_Date;
        //            categoryList[0].Apply_Start_Date = objCategory.Apply_Start_Date;

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
        //            db.OD_CATEGORies.InsertOnSubmit(objCategory);
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
        //// Delete mutilple record categry
        //[HttpPost]
        //public JToken CategoryDelete([FromBody] String CategoryCodeList)
        //{
        //    int deleteResult = 0;
        //    String[] arrayCategoryCode = CategoryCodeList.Split(',');
        //    var categoryList = db.OD_CATEGORies.Where(i => arrayCategoryCode.Contains(i.Category_Code)).ToList();
        //    try
        //    {
        //        foreach (var category in categoryList)
        //        {
        //            db.OD_CATEGORies.DeleteOnSubmit(category);
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
        //// get List Category by Category code
        //[HttpPost]
        //public JToken getCategoryBycategoryCode([FromBody]String CategoryCodeList)
        //{
        //    String[] arrayCategoryCode = CategoryCodeList.Split(',');
        //    var dt = db.OD_CATEGORies.Where(i => arrayCategoryCode.Contains(i.Category_Code)).ToList();
        //    if (dt == null)
        //    {
        //        return JObject.FromObject(new { type = 0, data = 0 });
        //    }
        //    return JObject.FromObject(new { type = 0, data = dt });
        //}
    }
}