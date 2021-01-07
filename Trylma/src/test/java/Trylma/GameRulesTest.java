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
		} catch(Exception e) {
			e.printStackTrace();
		}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		client1.input.nextLine();
		client1.input.nextLine();
		client1.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		response=client1.input.nextLine();
		if(response.equals("MESSAGE Your move")) {
			client1.output.println("SELECT 10|13");
			response = client1.input.nextLine();
			assertEquals(response,"INVALID_SELECTION This is not your peg!");
			client1.output.println("SELECT 6|6");
			response=client1.input.nextLine();
			assertEquals(response,"INVALID_SELECTION This field is empty!");
		} else {
			client2.output.println("SELECT 4|0");
			response = client2.input.nextLine();
			assertEquals(response,"INVALID_SELECTION This is not your peg!");
			client2.output.println("SELECT 6|6");
			response=client2.input.nextLine();
			assertEquals(response,"INVALID_SELECTION This field is empty!");
		}
	}
	
	@Test
	public void validSelectionTest() { 
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
		} catch(Exception e) {
			e.printStackTrace();
		}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		client1.input.nextLine();
		client1.input.nextLine();
		client1.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		response = client1.input.nextLine();
		if(response.equals("MESSAGE Your move")) {
			client1.output.println("SELECT 4|0");
			response = client1.input.nextLine();
		} else {
			client2.output.println("SELECT 10|13");
			response = client2.input.nextLine();
		}
		assertEquals(response,"VALID_SELECTION");
	}
	
	@Test
	public void invalidMoveTest() { //move not allowed, check both single and double jump
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
		} catch(Exception e) {
			e.printStackTrace();
		}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		client1.input.nextLine();
		client1.input.nextLine();
		client1.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		response = client1.input.nextLine();
		if(response.equals("MESSAGE Your move")) {
			client1.output.println("SELECT 4|3");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client1.output.println("MOVE 4|5"); //jump not over a peg
			response = client1.input.nextLine();
			assertEquals(response,"INVALID_MOVE This move is not allowed!");
			client1.output.println("SELECT 4|2");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client1.output.println("MOVE 4|5"); //too long jump
			response = client1.input.nextLine();
			client1.output.println("SELECT 4|2");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client1.output.println("MOVE 4|4");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_MOVE");
			client1.output.println("SELECT 4|4"); //try to chain double and single jump
			response = client1.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client1.output.println("MOVE 4|5");
			response = client1.input.nextLine();
			assertEquals(response,"INVALID_MOVE This move is not allowed!");
		} else {
			client2.output.println("SELECT 9|13");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client2.output.println("MOVE 9|11"); //jump not over a peg
			response = client2.input.nextLine();
			assertEquals(response,"INVALID_MOVE This move is not allowed!");
			client2.output.println("SELECT 10|14");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client2.output.println("MOVE 10|11"); //too long jump
			response = client2.input.nextLine();
			assertEquals(response,"INVALID_MOVE This move is not allowed!");
			client2.output.println("SELECT 10|14");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client2.output.println("MOVE 10|12");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_MOVE");
			client2.output.println("SELECT 10|12"); //try to chain double and single jump
			response = client2.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client2.output.println("MOVE 10|11");
			response = client2.input.nextLine();
			assertEquals(response,"INVALID_MOVE This move is not allowed!");
		}
	}
	
	@Test
	public void validMoveTest() { //single  jump 
		try {
		 client1 = new DummyClient();
		 client2 = new DummyClient();
		} catch(Exception e) {
			e.printStackTrace();
		}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		client1.input.nextLine();
		client1.input.nextLine();
		client1.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		response = client1.input.nextLine();
		if(response.equals("MESSAGE Your move")) {
			client1.output.println("SELECT 4|3");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client1.output.println("MOVE 4|4");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_MOVE");
		} else {
			client2.output.println("SELECT 10|13");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client2.output.println("MOVE 10|12");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_MOVE");
		}
	}
	@Test
	public void validMoveTestDoubleJump() { // double jump 
		try {
		 client1 = new DummyClient();
		 client2 = new DummyClient();
		} catch(Exception e) {
			e.printStackTrace();
		}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		client1.input.nextLine();
		client1.input.nextLine();
		client1.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		response = client1.input.nextLine();
		if(response.equals("MESSAGE Your move")) {
			client1.output.println("SELECT 4|2");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client1.output.println("MOVE 4|4");
			response = client1.input.nextLine();
			assertEquals(response,"VALID_MOVE");
		} else {
			client2.output.println("SELECT 11|14");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_SELECTION");
			client2.output.println("MOVE 11|12");
			response = client2.input.nextLine();
			assertEquals(response,"VALID_MOVE");
		}
	}
	@Test
	public void notYourTurnTest() {
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
		} catch(Exception e) {
			e.printStackTrace();
		}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		client1.input.nextLine();
		client1.input.nextLine();
		client1.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		response = client1.input.nextLine();
		if(response.equals("MESSAGE Your move")) {
			client2.output.println("SELECT 11|14");
			response = client2.input.nextLine();
			assertEquals(response,"INVALID_SELECTION Not your turn!");
			client2.output.println("END_TURN");
			response = client2.input.nextLine();
			assertEquals(response,"MESSAGE Not your turn!");
		} else {
			client1.output.println("SELECT 4|0");
			response = client1.input.nextLine();
			assertEquals(response,"INVALID_SELECTION Not your turn!");
			client1.output.println("END_TURN");
			response = client1.input.nextLine();
			assertEquals(response,"MESSAGE Not your turn!");
		}
	}
	@Test
	public void endTurnTest() { //end turn on demand
		try {
			 client1 = new DummyClient();
			 client2 = new DummyClient();
		} catch(Exception e) {
			e.printStackTrace();
		}
		client1.board.fillBoard(2);
		client2.board.fillBoard(2);
		client1.input.nextLine();
		client1.input.nextLine();
		client1.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		client2.input.nextLine();
		response = client1.input.nextLine();
		if(response.equals("MESSAGE Your move")) {
			client1.output.println("END_TURN");
			response = client1.input.nextLine();
			assertEquals(response,"MESSAGE Blue's move");
		} else {
			client2.output.println("END_TURN");
			response = client2.input.nextLine();
			assertEquals(response,"MESSAGE Red's move");
		}
	}
	
}
