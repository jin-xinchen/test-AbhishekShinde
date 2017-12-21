 package de.hybris.platform.customerreview.impl;
 
 import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.customerreview.dao.CustomerReviewDao;
import de.hybris.platform.customerreview.jalo.CustomerReview;
import de.hybris.platform.customerreview.jalo.CustomerReviewManager;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.servicelayer.internal.service.AbstractBusinessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
 


 public class ProductExtendedDefaultCustomerReviewService
   extends DefaultCustomerReviewService
 {
   private CustomerReviewDao customerReviewDao;
   
   /**
    * a way to get a product’s total number of customer reviews whose ratings are within a given range (inclusive).
    *
    * @param product
    * @param rating
    * @return Integer  A product’s total number.
    */
   public Integer getTotalNumberOfProductReviewsWithinRating(ProductModel product, Double rating)
   {
     return CustomerReviewManager.getInstance().getTotalNumberOfProductReviewsWithinRating(
    		 (Product)getModelService().getSource(product), rating);
   }
   

 }
