public class Webpage {
	private String pageData;
	private String title;
	private String body;

	public Webpage(String pageData) {
		if (pageData.equals("PageNotFound")) {
			this.title = "";
			this.body = "Error: Page Not Found";
		}
		else {
			this.pageData = pageData;
			this.title = getTagData("title");
			this.body = getTagData("body");
		}
	}

	public String getTagData(String tagName) {
		String openTag = "<" + tagName + ">";
		String closeTag = "</" + tagName + ">";

		int startIndex = pageData.indexOf(openTag) + openTag.length();
		int closeIndex = pageData.indexOf(closeTag);

		String body;
		try {
			body = pageData.substring(startIndex, closeIndex);
			body = body.replaceAll("\\t ", "");
			body = body.replaceAll("<script>[\\s\\S]*?</script>", "");
			body = body.replaceAll("<--.*-->", "");
			body = body.replaceAll("<[\\s\\S]*?>", "");
			body = body.replaceAll("[\r\n]+", "\n");
			body = body.trim();
		} catch (StringIndexOutOfBoundsException e) {
			body = "PageNotFound";
		}

		
		return body;
	}

	public String getTitle() {
		return this.title;
	}

	public String getBody() {
		return this.body;
	}
}
