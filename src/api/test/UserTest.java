package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	
	Faker faker;
	User userpayload;
	
	@BeforeClass
	public void setup() {
		
		faker = new Faker();
		userpayload = new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		Response response = UserEndpoints.createUser(userpayload);
		
		response.then().log().all();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2)
	public void testGetUser() {
		Response response = UserEndpoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		AssertJUnit.assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority = 3)
	public void testUpdateUser() {
		
		//updating data
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints.updateUser(this.userpayload.getUsername(),userpayload);
		
		response.then().log().body();
		
		AssertJUnit.assertEquals(response.statusCode(), 200);
		
		//checking data after update
		Response responseafterupdate = UserEndpoints.readUser(this.userpayload.getUsername());
		AssertJUnit.assertEquals(responseafterupdate.statusCode(), 200);
		
		
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		Response response = UserEndpoints.deleteUser(this.userpayload.getUsername());
		AssertJUnit.assertEquals(response.statusCode(), 200);
		
	}
	
	

}
