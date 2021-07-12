package chat;


import java.util.Hashtable;

public class mchat {

		private Hashtable<String,String> htMS = new Hashtable<>();
		public String getChatResult(String sKey) {
			String str = htMS.get(sKey);
			if(str == null)
				return "";
			else
				return str;
		}
		public mchat() {
			htMS.put("hey siri", "我在");
			htMS.put("嘿嘿","嘿嘿");
			htMS.put("今晚月色很好~","风也温柔~");
			htMS.put("baibai", "bye");
		
	}

}
