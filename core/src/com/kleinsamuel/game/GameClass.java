package com.kleinsamuel.game;

import com.badlogic.gdx.Game;
import com.kleinsamuel.game.model.entities.OtherPlayer;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.startscreen.StartScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GameClass extends Game {

	private String serverName = "87.160.59.32";
	private String port = "8080";
	private IO.Options socketOptions;

	private final float UPDATE_TIMER = 1/40f;
	private float timer;

    public boolean CONNECTED = false;
	public boolean signInSuccessfull = false;

	private Socket socket;
	public StartScreen startScreen;
	public PlayScreen playScreen;

	public HashMap<String, OtherPlayer> otherPlayers;
	public HashMap<Integer, NPC> npcs;

	@Override
	public void create() {
		DebugMessageFactory.printNormalMessage("STARTED GAME INSTANCE.");

		socketOptions = new IO.Options();
		socketOptions.timeout = 5000;
		socketOptions.reconnectionAttempts = 0;

		otherPlayers = new HashMap<String, OtherPlayer>();
		npcs = new HashMap<Integer, NPC>();

		startScreen = new StartScreen(this);

		setScreen(startScreen);
	}

	public void connect(){
		connectSocket();
		configSocketEvents();
	}

	public void registerPlayer(String username, String password){

		signInSuccessfull = false;

		JSONObject data = new JSONObject();

		try {
			data.put("username", username);
			data.put("password", password);

			socket.emit("registerNewUser", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("ERROR SENDING REGISTERING DATA!");
		}
	}

	public void updateServer_Position(float delta, Player player){
		timer += delta;

		if(timer >= UPDATE_TIMER && playScreen != null){
			JSONObject data = new JSONObject();
			try {
				data.put("name", player.content.name);
				data.put("x", player.content.x);
				data.put("y", player.content.y);
				data.put("level", player.content.level);
				data.put("xMove", player.xMove);
				data.put("yMove", player.yMove);
				data.put("xPos", player.xPos);

				socket.emit("playerMoved", data);

			} catch (JSONException e){
				DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING UPDATE DATA");
			}
		}
	}

	public void updateServer_Health(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("currHealth", p.content.current_health);
			data.put("maxHealth", p.content.health);

			socket.emit("healthUpdate", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING HEALTH UPDATE");
		}
	}

	public void updateServer_Mana(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("currMana", p.content.current_mana);
			data.put("maxMana", p.content.mana);

			socket.emit("manaUpdate", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING MANA UPDATE");
		}
	}

	public void updateServer_Level(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("level", p.content.level);

			socket.emit("levelUpdate", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING LEVEL UPDATE");
		}
	}

	public void sendInitialInfo(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("name", p.content.name);
			data.put("x", p.content.x);
			data.put("y", p.content.y);
			data.put("level", p.content.level);
			data.put("xMove", p.xMove);
			data.put("yMove", p.yMove);
			data.put("xPos", p.xPos);
			data.put("currHealth", p.content.current_health);
			data.put("maxHealth", p.content.health);
			data.put("currMana", p.content.current_mana);
			data.put("maxMana", p.content.mana);

			socket.emit("initPlayer", data);

		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING INITIAL INFO");
		}
	}

	public void connectSocket(){

		if(socket != null){
			socket.disconnect();
			socket.close();
		}

		DebugMessageFactory.printNormalMessage("CONNECTING TO SERVER ["+serverName+":"+port+"]...");
		try {
			socket = null;
			socket = IO.socket("http://"+serverName+":"+port, socketOptions);
			socket.connect();
		} catch( Exception e){
			DebugMessageFactory.printNormalMessage("COULD NOT CONNECT TO SERVER!");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void configSocketEvents(){
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				DebugMessageFactory.printInfoMessage("Socket IO Connected!");
				CONNECTED = true;
			}
		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				CONNECTED = false;
			}
		}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				DebugMessageFactory.printInfoMessage("TIMEOUT");
			}
		}).on("socketID", new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					DebugMessageFactory.printInfoMessage("Received ID: ["+id+"]");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).on("newPlayer", new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					DebugMessageFactory.printInfoMessage("New Player connected: ["+id+"]");

					if(playScreen != null) {
						playScreen.addOtherPlayer(id);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).on("playerDisconnected", new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					DebugMessageFactory.printInfoMessage("Player disconnected: ["+id+"]");
					if(playScreen != null) {
						playScreen.removeOtherPlayer(id);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).on("getPlayers", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONArray objects = (JSONArray) args[0];
				try {
					for(int i = 0; i < objects.length(); i++){

						String id = objects.getJSONObject(i).getString("id");
						String name = objects.getJSONObject(i).getString("name");
						int entityX = objects.getJSONObject(i).getInt("x");
						int entityY = objects.getJSONObject(i).getInt("y");
						int xMove = objects.getJSONObject(i).getInt("xMove");
						int yMove = objects.getJSONObject(i).getInt("yMove");
						int xPos = objects.getJSONObject(i).getInt("xPos");

						if(playScreen != null) {
							playScreen.addOtherPlayer(id, name, entityX, entityY, xMove, yMove, xPos);
						}
					}
				} catch(JSONException e){

				}
			}
		}).on("playerMoved", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					String name = data.getString("name");
					int entityX = data.getInt("x");
					int entityY = data.getInt("y");
					int xMove = data.getInt("xMove");
					int yMove = data.getInt("yMove");
					int xPos = data.getInt("xPos");

					if(playScreen != null) {
						playScreen.addOtherPlayer(id, name, entityX, entityY, xMove, yMove, xPos);
					}
				} catch(JSONException e){

				}
			}
		}).on("registerNewUserAnswer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				JSONObject data = (JSONObject) args[0];
				try {

					boolean success = data.getBoolean("status");
					signInSuccessfull = success;

					DebugMessageFactory.printInfoMessage("GOT ANSWER FOR SIGN UP REQUEST: "+success);

				} catch(JSONException e){

				}

				startScreen.signUpScreen.changeStatusText = false;

			}
		});
	}

	@Override
	public void render () {
		super.render();
	}


	@Override
	public void pause() {
		super.pause();
		if(playScreen != null && playScreen.player != null) {
			playScreen.player.content.writeToFile();
		}
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
