package at.partyspot.Generators;

public class CodeGenerator {

	public static String generateUserCode () {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder code = new StringBuilder(10);
  
        for (int i = 0; i < 10; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            code.append(AlphaNumericString
                          .charAt(index));
        }
  
        return code.toString();
	}
	
}
