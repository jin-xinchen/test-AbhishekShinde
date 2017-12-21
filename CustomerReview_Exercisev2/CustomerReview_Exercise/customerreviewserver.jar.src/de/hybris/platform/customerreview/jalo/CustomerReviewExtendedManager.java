 package de.hybris.platform.customerreview.jalo;
 
 import de.hybris.platform.core.Constants.USER;
 import de.hybris.platform.customerreview.constants.GeneratedCustomerReviewConstants.TC;
 import de.hybris.platform.jalo.JaloSession;
 import de.hybris.platform.jalo.SearchResult;
 import de.hybris.platform.jalo.SessionContext;
 import de.hybris.platform.jalo.extension.ExtensionManager;
 import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;
 import de.hybris.platform.jalo.product.Product;
 import de.hybris.platform.jalo.type.TypeManager;
 import de.hybris.platform.jalo.user.User;
 import de.hybris.platform.jalo.user.UserGroup;
 import de.hybris.platform.jalo.user.UserManager;
 import de.hybris.platform.util.JspContext;

 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;

import org.apache.log4j.Logger;
 
public class CustomerReviewExtendedManager
 extends CustomerReviewManager
{
 private static final Logger LOG = Logger.getLogger(CustomerReviewExtendedManager.class.getName());

/**
 *  To get a product’s total number of customer reviews whose ratings are within a given range (inclusive).
 * @param ctx
 * @param item
 * @param rating
 * @return Integer:  number of reviews
 */
public Integer getTotalNumberOfProductReviewsWithinRating(SessionContext ctx, Product item, Double rating)
{
	String query = "SELECT count(*) FROM {" + GeneratedCustomerReviewConstants.TC.CUSTOMERREVIEW + "} WHERE {" + 
			"product" + "} = ?product and { rating } <= ?rating";
	final Map<String, Object> params = new HashMap<String, Object>();
	params.put("product", item);
	params.put("rating", rating);
	List<Integer> result = FlexibleSearch.getInstance()
			.search(query, params, Collections.singletonList(Integer.class), true,true,0, -1).getResult();
	return (Integer)result.iterator().next();
} 

/**
 * Add the following additional checks before creating a customer review:
a.	Your service should read a list of curse words. This list should not be defined in Java class. 
b.	Check if Customer’s comment contains any of these curse words. If it does, throw an exception with a message.
c.	Check if the rating is not < 0.  If it is < 0, throw an exception with a message.

 * @param rating
 * @param headline
 * @param comment
 * @param user
 * @param product
 * @return CustomerReview
 * @throws JaloInvalidParameterException
 */
@Override
public CustomerReview createCustomerReview(Double rating, String headline, String comment, User user, Product product)
throws JaloInvalidParameterException
{
/*
a.	Your service should read a list of curse words. This list should not be defined in Java class. 
b.	Check if Customer’s comment contains any of these curse words. If it does, throw an exception with a message.
*/
	String curseWords= getProperty(ctx, "cursewords");
	String[] words = curseWords.split(" +");
	String commentLower = comment.toLowerCase();
	for(int i=0;i<words.length;i++){
		int n= commentLower.indexOf(words[i].toLowerCase());
		if(!(n==-1)){
		    throw new JaloInvalidParameterException(Localization.getLocalizedString("error.customerreview.invalidcomment.", 
		    	      new Object[] { comment, new String(words[i]) };			
		}
	}
	
/*
c.	Check if the rating is not < 0.  If it is < 0, throw an exception with a message.
*/
 if (rating != null)
 {
   if ((rating.doubleValue() < CustomerReviewConstants.getInstance().MINRATING))
   {
    throw new JaloInvalidParameterException(Localization.getLocalizedString("error.customerreview.invalidrating, which the rating is not < 0.", 
      new Object[] { rating, new Double(CustomerReviewConstants.getInstance().MINRATING),
      new Double(CustomerReviewConstants.getInstance().MAXRATING) }), 0);
   }	
 }
 return super.createCustomerReview(rating, headline, comment, user, product);	
}

}
