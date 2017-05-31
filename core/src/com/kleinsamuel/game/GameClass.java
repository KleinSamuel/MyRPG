package com.kleinsamuel.game;

import com.badlogic.gdx.Game;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GameClass extends Game {

	private String serverName = "87.160.62.238";
	private String port = "8080";

	private final float UPDATE_TIMER = 1/40f;
	private float timer;

	private Socket socket;
	public PlayScreen playScreen;

	@Override
	public void create() {
		DebugMessageFactory.printNormalMessage("STARTED GAME INSTANCE.");

		connectSocket();
		configSocketEvents();

		DebugMessageFactory.printNormalMessage("INITIALISING GAME...");

		setScreen(new PlayScreen(this));
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
		DebugMessageFactory.printNormalMessage("CONNECTING TO SERVER ["+serverName+":"+port+"]...");
		try {
			socket = IO.socket("http://"+serverName+":"+port);
			socket.connect();
		} catch( Exception e){
			DebugMessageFactory.printNormalMessage("COULD NOT CONNECT TO SERVER!");
			e.printStackTrace();
			System.exit(1);
		}
		DebugMessageFactory.printNormalMessage("CONNECTION ESTABLISHED.");
	}

	public void configSocketEvents(){
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				DebugMessageFactory.printInfoMessage("Socket IO Connected!");
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
					playScreen.addOtherPlayer(id);
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
					playScreen.removeOtherPlayer(id);
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

						playScreen.addOtherPlayer(id, name, entityX, entityY, xMove, yMove, xPos);
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

					playScreen.addOtherPlayer(id, name, entityX, entityY, xMove, yMove, xPos);
				} catch(JSONException e){

				}
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
		playScreen.player.content.writeToFile();
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
