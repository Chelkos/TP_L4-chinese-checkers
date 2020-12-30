package Trylma;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.mockito.Mockito;

import connection.TrilmaClient;
import exceptions.IllegalSelectionException;

//checking server's response for each action
public class GameRulesTest {

	@Test
	public void invalidSelectionTest() { //not your peg, empty field
		try {
			
		DummyClient client1 = new DummyClient();
		DummyClient client2 = new DummyClient();
		client1.board.fillBoard(2);
		String response =  client1.input.nextLine();
		response = client1.input.nextLine();
		assertEquals(response,"PLAYERS: 2");
		response = client1.input.nextLine();
		response = client1.input.nextLine();
		assertEquals(response,"MESSAGE Your move");
		client1.output.println("SELECT 6|6");
		response = client1.input.nextLine();
		assertEquals(response,"INVALID_SELECTION This field is empty!");
		client1.output.println("SELECT 4|0");
		response = client1.input.nextLine();
		assertEquals(response,"VALID_SELECTION");
		}
		catch(Exception e)
		{
			assertEquals(1, 2);
		}
	}
	
	@Test
	public void validSelectionTest() { 
		
	}
	
	@Test
	public void invalidMoveTest() { //move not allowed, field already occupied, check both single and double jump
		
	}
	
	@Test
	public void validMoveTest() { //check both single and double jump 
		
	}
	
	@Test
	public void notYourTurnTest() {
		
	}
	
}
