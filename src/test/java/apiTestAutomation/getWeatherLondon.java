package apiTestAutomation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;

@Test
public class getWeatherLondon {

	private String baseUri;

	@BeforeClass
	public void setUp() {
		baseUri = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";
	}

	public void testResponseCode() {
		Response response = RestAssured.get(baseUri);

		int statusCode = response.getStatusCode();

		//Verifying if the response body is successful
		Assert.assertEquals(statusCode, 200);

	}

	public void testResponseBody() {
		Response response = RestAssured.get(baseUri);

		ResponseBody body = response.getBody();
		String bodyStringValue = body.asString();

		Assert.assertTrue(bodyStringValue.contains("name"));

		JsonPath jsonPathEvaluator = response.jsonPath();
		String firstName = jsonPathEvaluator.get("name");
		
		//Verifying if the response body contains "London"
		Assert.assertEquals(firstName, "London");

	}

}
