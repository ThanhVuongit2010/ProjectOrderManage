using Newtonsoft.Json.Linq;
using OrderManager_Api.Models;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OrderManager_Api.Controllers
{
    public class ProductController : ApiController
    {
        OrderManagerModelsDataContext db = new OrderManagerModelsDataContext();
        // Insert or update Company
        [HttpPost]
        public JToken ProductInsert([FromBody] JObject products)
        {
            var insertRusult = 0;
            OD_PRODUCT objProduct = new OD_PRODUCT();

            JToken product = products["product"];
            objProduct.Product_Name = (String)product["Product_Name"];
            objProduct.Product_Name_EN = (String)product["Product_Name_EN"];
            String typical = (String)product["Typical"];
            if (!String.IsNullOrEmpty(typical)) {
                objProduct.Typical = Int32.Parse(typical);
            }
            String price = (String)product["Price"];
            if (!String.IsNullOrEmpty(price))
            {
                objProduct.Price = Decimal.Parse(price);
            }
            String promotions = (String)product["Promotions"];
            if (!String.IsNullOrEmpty(promotions))
            {
                objProduct.Promotions = Int32.Parse(promotions);
            }
            String dayStart = (String)product["Promotions_Start_Date"];
            DateTime promotionsStart = DateTime.ParseExact(dayStart, "yyyy-MM-dd", CultureInfo.InvariantCulture);
            objProduct.Promotions_Start_Date = promotionsStart;

            String dayEnd = (String)product["Promotions_End_Date"];
            DateTime promotionsEnd = DateTime.ParseExact(dayStart, "yyyy-MM-dd", CultureInfo.InvariantCulture);
            objProduct.Promotions_End_Date = promotionsStart;

            objProduct.Picture = (String)product["Picture"];
            String inventory_Number = (String)product["Inventory_Number"];
            if (!String.IsNullOrEmpty(inventory_Number))
            {
                objProduct.Inventory_Number = Int32.Parse(inventory_Number);
            }
            String order_Number = (String)product["Order_Number"];
            if (!String.IsNullOrEmpty(order_Number))
            {
                objProduct.Order_Number = Int32.Parse(order_Number);
            }
            String sort = (String)product["Sort"];
            if (!String.IsNullOrEmpty(sort))
            {
                objProduct.Promotions = Int32.Parse(sort);
            }
            String numberMonth = (String)product["UsingTime"];
            DateTime now = DateTime.Now;
            objProduct.Apply_Start_Date = now;
            DateTime endDate = now.AddMonths(Int32.Parse(numberMonth));
            objProduct.Apply_End_Date = endDate;

            var productList = db.OD_PRODUCTs.Where(i => i.Product_Code == objProduct.Product_Code).ToList();
            if (productList.Count > 0)
            {
                try
                {
                    productList[0].Product_Code = objProduct.Product_Code;
                    productList[0].Product_Name = objProduct.Product_Name;
                    productList[0].Product_Name_EN = objProduct.Product_Name_EN;
                    productList[0].Branch_Code = objProduct.Branch_Code;
                    productList[0].Category_Code = objProduct.Category_Code;
                    productList[0].Typical = objProduct.Typical;
                    productList[0].Price = objProduct.Price;
                    productList[0].Promotions = objProduct.Promotions;
                    productList[0].Promotions_Start_Date = objProduct.Promotions_Start_Date;
                    productList[0].Promotions_End_Date = objProduct.Promotions_End_Date;
                    productList[0].Order_Number = objProduct.Order_Number;
                    productList[0].Inventory_Number = objProduct.Inventory_Number;
                    productList[0].Sort = objProduct.Sort;
                    productList[0].Picture = objProduct.Picture;
                    productList[0].Apply_Start_Date = objProduct.Apply_Start_Date;
                    productList[0].Apply_Start_Date = objProduct.Apply_Start_Date;

                    db.SubmitChanges();
                    insertRusult = 1;
                }
                catch (Exception ex)
                {
                    return JObject.FromObject(new { type = 0, data = ex });
                }
            }
            else
            {
                try
                {
                    db.OD_PRODUCTs.InsertOnSubmit(objProduct);
                    db.SubmitChanges();
                    insertRusult = 1;
                }
                catch (Exception ex)
                {
                    return JObject.FromObject(new { type = 0, data = ex });
                }

            }
            return JObject.FromObject(new { type = 0, data = insertRusult });
        }
        // Delete mutilple record categry
        //[HttpPost]
        //public JToken ProductDelete([FromBody] String productCodeList)
        //{
        //    int deleteResult = 0;
        //    String[] arayProductCodeList = productCodeList.Split(',');
        //    var productList = db.OD_PRODUCTs.Where(i => arayProductCodeList.Contains(i.Product_Code)).ToList();
        //    try
        //    {
        //        foreach (var product in productList)
        //        {
        //            db.OD_PRODUCTs.DeleteOnSubmit(product);
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
        //// get List Company by product
        //[HttpPost]
        //public JToken getProductByproductCode([FromBody]String productCodeList)
        //{
        //    String[] arayProductCodeList = productCodeList.Split(',');
        //    var dt = db.OD_PRODUCTs.Where(i => arayProductCodeList.Contains(i.Product_Code)).ToList();
        //    if (dt == null)
        //    {
        //        return JObject.FromObject(new { type = 0, data = 0 });
        //    }
        //    return JObject.FromObject(new { type = 0, data = dt });
        //}
    }
}