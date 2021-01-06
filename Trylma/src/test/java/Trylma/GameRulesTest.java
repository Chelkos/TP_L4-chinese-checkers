package Trylma;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.mockito.Mockito;

import connection.TrilmaClient;
import exceptions.IllegalSelectionException;

//checking server's response for each action
public class GameRulesTest {
	DummyClient client1;
	DummyClient client2;
	String response;
	@Test
	public void invalidSelectionTest() { //not your peg, empty field
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
				}
				catch(Exception e)
				{
					
				}
			client1.board.fillBoard(2);
			client2.board.fillBoard(2);
		 response =  client1.input.nextLine();
		response = client1.input.nextLine();
		assertEquals(response,"PLAYERS: 2");
		response = client1.input.nextLine();
		response = client1.input.nextLine();
		assertEquals(response,"MESSAGE Your move");
		client1.output.println("SELECT 6|6");
		response = client1.input.nextLine();
		assertEquals(response,"INVALID_SELECTION This field is empty!");

	}
	
	@Test
	public void validSelectionTest() { 
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
				}
				catch(Exception e)
				{
					
				}
			client1.board.fillBoard(2);
			client2.board.fillBoard(2);
			response = client1.input.nextLine();
			response = client1.input.nextLine();
			response =  client1.input.nextLine();
		client1.output.println("SELECT 4|0");
		response = client1.input.nextLine();
		response = client1.input.nextLine();
		assertEquals(response,"VALID_SELECTION");
	}
	
	@Test
	public void invalidMoveTest() { //move not allowed, field already occupied, check both single and double jump
	

	}
	
	@Test
	public void validMoveTest() { //single  jump 
		try {
		 client1 = new DummyClient();
		 client2 = new DummyClient();
			}
			catch(Exception e)
			{
				
			}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		response = client1.input.nextLine();
		response = client1.input.nextLine();
		response =  client1.input.nextLine();
	client1.output.println("SELECT 4|3");
	response = client1.input.nextLine();
	response = client1.input.nextLine();
	assertEquals(response,"VALID_SELECTION");
	client1.output.println("MOVE 4|4");
	response = client1.input.nextLine();
	assertEquals(response,"VALID_MOVE");
	}
	@Test
	public void validMoveTestDoubleJump() { // double jump 
		try {
		 client1 = new DummyClient();
		 client2 = new DummyClient();
			}
			catch(Exception e)
			{
				
			}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		response = client1.input.nextLine();
		response = client1.input.nextLine();
		response =  client1.input.nextLine();
	client1.output.println("SELECT 4|2");
	response = client1.input.nextLine();
	response = client1.input.nextLine();
	assertEquals(response,"VALID_SELECTION");
	client1.output.println("MOVE 4|4");
	response = client1.input.nextLine();
	assertEquals(response,"VALID_MOVE");
	}
	@Test
	public void notYourTurnTest() {
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
				}
				catch(Exception e)
				{
					
				}
			client1.board.fillBoard(2);
			client2.board.fillBoard(2);
			response = client2.input.nextLine();
			response = client2.input.nextLine();
			response =  client2.input.nextLine();

			client2.output.println("SELECT 4|0");
			response =  client2.input.nextLine();
			
			assertEquals(response,"INVALID_SELECTION Not your turn!");
	}
	@Test
	public void endTurnTest() {
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
				}
				catch(Exception e)
				{
					
				}
			client1.board.fillBoard(2);
			client2.board.fillBoard(2);
			response = client1.input.nextLine();
			response = client1.input.nextLine();
			response =  client1.input.nextLine();
			response = client1.input.nextLine();
			client1.output.println("END_TURN");
			response = client1.input.nextLine();
			assertEquals(response,"MESSAGE Not your turn!");
	}
	
}
